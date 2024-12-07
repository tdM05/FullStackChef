package data_access;

import app.SessionUser;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import entity.CommonDietaryRestriction;
import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of DietaryRestrictionDataAccessInterface.
 * Handles saving and loading dietary restrictions for the user.
 */
public class DietaryRestrictionDataAccessObject implements DietaryRestrictionDataAccessInterface {

    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112"; // Base API URL
    private static final int SUCCESS_CODE = 200; // HTTP status code for success
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final OkHttpClient client;

    public DietaryRestrictionDataAccessObject() {
        this.client = new OkHttpClient();
    }

    /**
     * Saves the dietary restrictions for the current user by sending a PUT request.
     *
     * @param commonDietaryRestriction the dietary restrictions to save
     * @throws IOException if the request fails or the backend does not save correctly
     */
    @Override
    public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
        User user = getCurrentUser();

        // First, load the current user info from the backend
        HttpUrl url = HttpUrl.parse(BASE_URL + "/user")
                .newBuilder()
                .addQueryParameter("username", user.getName())
                .addQueryParameter("password", user.getPassword())
                .build();

        Request getRequest = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        JSONObject info;
        try (Response getResponse = client.newCall(getRequest).execute()) {
            if (getResponse.code() == SUCCESS_CODE && getResponse.body() != null) {
                String responseBody = getResponse.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONObject userJson = jsonResponse.optJSONObject("user");

                if (userJson == null) {
                    System.err.println("Backend response does not contain 'user' object.");
                    throw new IOException("Backend response does not contain 'user' object.");
                }

                // Extract current info object, or create a new one if not present
                info = userJson.optJSONObject("info");
                if (info == null) {
                    info = new JSONObject();
                }
            } else {
                System.err.println("Failed to load existing user info: " + getResponse.message());
                throw new IOException("Failed to load existing user info: " + getResponse.message());
            }
        } catch (JSONException e) {
            System.err.println("Error parsing existing user info: " + e.getMessage());
            throw new IOException("Error parsing existing user info: " + e.getMessage(), e);
        }

        // Now update the dietaryRestrictions field in the existing info
        info.put("dietaryRestrictions", new JSONArray(commonDietaryRestriction.getDiets()));

        // Prepare the request body for saving the updated info
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", user.getName());
        requestBody.put("password", user.getPassword());
        requestBody.put("info", info);

        // Print the request body for debugging
        System.out.println("Saving Dietary Restrictions Request Body: " + requestBody.toString());

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse(CONTENT_TYPE_JSON));
        Request putRequest = new Request.Builder()
                .url(BASE_URL + "/modifyUserInfo")
                .put(body)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        System.out.println("Sending PUT request to save dietary restrictions...");

        try (Response response = client.newCall(putRequest).execute()) {
            System.out.println("Save Dietary Restrictions Response Code: " + response.code());
            System.out.println("Save Dietary Restrictions Response Message: " + response.message());

            if (response.code() != SUCCESS_CODE) {
                System.err.println("Failed to save dietary restrictions: " + response.message());
                throw new IOException("Failed to save dietary restrictions: " + response.message());
            }

            String responseBody = response.body().string();
            System.out.println("Save Dietary Restrictions Response Body: " + responseBody);

            try {
                JSONObject jsonResponse = new JSONObject(responseBody);
                String message = jsonResponse.optString("message", "Dietary restrictions updated.");
                System.out.println("Message from backend: " + message);
                System.out.println("Dietary restrictions saved successfully for user: " + user.getName());
            } catch (JSONException e) {
                System.err.println("Error parsing backend response: " + e.getMessage());
                throw new IOException("Error parsing backend response: " + e.getMessage(), e);
            }
        }
    }


    /**
     * Loads the dietary restrictions for the current user by sending a GET request.
     *
     * @return the loaded dietary restrictions
     * @throws IOException if the request fails or parsing fails
     */
    @Override
    public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
        User user = getCurrentUser();

        // Include password in the GET request for authentication
        HttpUrl url = HttpUrl.parse(BASE_URL + "/user")
                .newBuilder()
                .addQueryParameter("username", user.getName())
                .addQueryParameter("password", user.getPassword()) // Added password for authentication
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        System.out.println("Sending GET request to load dietary restrictions...");

        try (Response response = client.newCall(request).execute()) {
            // Print the response code and message for debugging
            System.out.println("Load Dietary Restrictions Response Code: " + response.code());
            System.out.println("Load Dietary Restrictions Response Message: " + response.message());

            if (response.code() == SUCCESS_CODE && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Load Dietary Restrictions Response Body: " + responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONObject userJson = jsonResponse.optJSONObject("user");

                if (userJson == null) {
                    System.err.println("Backend response does not contain 'user' object.");
                    throw new IOException("Backend response does not contain 'user' object.");
                }

                JSONObject infoJson = userJson.optJSONObject("info"); // Use optJSONObject to handle null

                if (infoJson != null && infoJson.has("dietaryRestrictions")) {
                    JSONArray restrictionsArray = infoJson.getJSONArray("dietaryRestrictions");
                    List<String> restrictions = new ArrayList<>();
                    for (int i = 0; i < restrictionsArray.length(); i++) {
                        restrictions.add(restrictionsArray.getString(i));
                    }
                    System.out.println("Loaded dietary restrictions for user: " + user.getName() + " -> " + restrictions);
                    return new CommonDietaryRestriction(restrictions);
                } else {
                    System.out.println("No dietary restrictions found for user: " + user.getName());
                }
            } else {
                System.err.println("Failed to load dietary restrictions: " + response.message());
                throw new IOException("Failed to load dietary restrictions: " + response.message());
            }
        } catch (JSONException ex) {
            System.err.println("Error parsing dietary restrictions: " + ex.getMessage());
            throw new IOException("Error parsing dietary restrictions: " + ex.getMessage(), ex);
        }

        // Return empty restrictions if not found or "info" is null
        return new CommonDietaryRestriction(new ArrayList<>());
    }


    /**
     * Retrieves the currently logged-in user.
     *
     * @return the User object
     * @throws IOException if no user is logged in
     */
    private User getCurrentUser() throws IOException {
        User user = SessionUser.getInstance().getUser();
        if (user == null) {
            System.err.println("No user is logged in. Please log in to continue.");
            throw new IOException("No user is logged in. Please log in to continue.");
        }
        return user;
    }
}
