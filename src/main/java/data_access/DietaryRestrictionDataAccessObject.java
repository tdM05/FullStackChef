package data_access;

import app.SessionUser;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
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
 */
public class DietaryRestrictionDataAccessObject implements DietaryRestrictionDataAccessInterface {

    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112"; // Base API URL
    private static final int SUCCESS_CODE = Constants.SUCCESS_CODE;
    private static final String CONTENT_TYPE_JSON = "application/json";

    private final OkHttpClient client;

    public DietaryRestrictionDataAccessObject() {
        this.client = new OkHttpClient();
    }

    @Override
    public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
        User user = getCurrentUser();

        // Prepare the JSON request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", user.getName());
        requestBody.put("password", user.getPassword());
        requestBody.put("dietaryRestrictions", new JSONArray(commonDietaryRestriction.getDiets()));

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse(CONTENT_TYPE_JSON));
        Request request = new Request.Builder()
                .url(BASE_URL + "/modifyUserInfo")
                .method("PUT", body)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != SUCCESS_CODE) {
                throw new IOException("Failed to save dietary restrictions: " + response.message());
            }
        }
    }

    @Override
    public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
        User user = getCurrentUser();

        Request request = new Request.Builder()
                .url(BASE_URL + "/user?username=" + user.getName())
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == SUCCESS_CODE) {
                JSONObject userJson = new JSONObject(response.body().string()).getJSONObject("user");
                if (userJson.has("info") && userJson.getJSONObject("info").has("dietaryRestrictions")) {
                    JSONArray restrictionsArray = userJson.getJSONObject("info").getJSONArray("dietaryRestrictions");
                    List<String> restrictions = new ArrayList<>();
                    for (int i = 0; i < restrictionsArray.length(); i++) {
                        restrictions.add(restrictionsArray.getString(i));
                    }
                    return new CommonDietaryRestriction(restrictions);
                }
            } else {
                throw new IOException("Failed to load dietary restrictions: " + response.message());
            }
        }
        return new CommonDietaryRestriction(new ArrayList<>());
    }

    private User getCurrentUser() throws IOException {
        User user = SessionUser.getInstance().getUser();
        if (user == null) {
            throw new IOException("No user is logged in. Please log in to continue.");
        }
        return user;
    }
}
