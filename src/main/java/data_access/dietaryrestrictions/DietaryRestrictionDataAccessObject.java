package data_access.dietaryrestrictions;

import app.SessionUser;
import data_access.Constants;
import data_access.DietaryRestrictionDataAccessInterface;
import entity.CommonDietaryRestriction;
import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object for Dietary Restrictions.
 * Implements the DietaryRestrictionDataAccessInterface.
 */
public class DietaryRestrictionDataAccessObject implements DietaryRestrictionDataAccessInterface {

    private static final int SUCCESS_CODE = Constants.SUCCESS_CODE;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = Constants.STATUS_CODE_LABEL;
    private static final String MESSAGE = Constants.MESSAGE;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DIETARY_RESTRICTIONS = Constants.DIETARY_RESTRICTIONS;

    private final OkHttpClient client;

    /**
     * Constructs a new DietaryRestrictionDataAccessObject with a default OkHttpClient.
     */
    public DietaryRestrictionDataAccessObject() {
        this.client = new OkHttpClient();
    }

    /**
     * Constructs a new DietaryRestrictionDataAccessObject with a provided OkHttpClient.
     * Useful for injecting mock clients during testing.
     *
     * @param client the OkHttpClient to use for API requests
     */
    public DietaryRestrictionDataAccessObject(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
        // Retrieve the current user from the session
        User user = getCurrentUser();

        // Prepare JSON request body
        JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());
        requestBody.put(DIETARY_RESTRICTIONS, new JSONArray(commonDietaryRestriction.getDiets()));

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse(CONTENT_TYPE_JSON));
        Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        // Send the request and handle response
        try (Response response = client.newCall(request).execute()) {
            JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(STATUS_CODE_LABEL) != SUCCESS_CODE) {
                throw new IOException("Failed to save dietary restrictions: " + responseBody.getString(MESSAGE));
            }
        } catch (Exception ex) {
            throw new IOException("Error during API call: " + ex.getMessage());
        }
    }

    @Override
    public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
        // Retrieve the current user from the session
        User user = getCurrentUser();

        String url = String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", user.getName());
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        // Send the request and handle response
        try (Response response = client.newCall(request).execute()) {
            JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                JSONObject userJSONObject = responseBody.getJSONObject("user");
                JSONObject info = userJSONObject.getJSONObject("info");

                List<String> diets = new ArrayList<>();
                if (info.has(DIETARY_RESTRICTIONS)) {
                    JSONArray restrictionsArray = info.getJSONArray(DIETARY_RESTRICTIONS);
                    for (int i = 0; i < restrictionsArray.length(); i++) {
                        diets.add(restrictionsArray.getString(i));
                    }
                }
                return new CommonDietaryRestriction(diets);
            } else {
                throw new IOException("Error retrieving dietary restrictions: " + responseBody.getString(MESSAGE));
            }
        } catch (Exception ex) {
            throw new IOException("Error during API call: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the current user from the session.
     *
     * @return the current user
     * @throws IOException if no user is logged in
     */
    private User getCurrentUser() throws IOException {
        User user = SessionUser.getInstance().getUser();
        if (user == null) {
            throw new IOException("No user is logged in. Please log in to continue.");
        }
        return user;
    }
}
