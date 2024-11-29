package data_access.user_profile;

import entity.user_profile.UserProfile;
import entity.user_profile.UserFactory;
import okhttp3.*;
import org.json.JSONObject;
import use_case.DataAccessException;

import java.io.IOException;

public class UserAuthDataAccessObject implements UserDataAccessObject {
    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112";
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final int SUCCESS_CODE = 200;

    private final UserFactory userFactory;

    public UserAuthDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public void saveUser(UserProfile user) throws DataAccessException {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", user.getName());
            requestBody.put("password", user.getPassword());

            JSONObject info = new JSONObject();
            info.put("displayName", user.getDisplayName());
            requestBody.put("info", info);

            RequestBody body = RequestBody.create(requestBody.toString(), MediaType.get(CONTENT_TYPE_JSON));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/modifyUserInfo")
                    .put(body)
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            Response response = CLIENT.newCall(request).execute();
            handleResponse(response, "Failed to save user profile.");
        } catch (IOException e) {
            throw new DataAccessException("Error communicating with server: " + e.getMessage());
        }
    }

    @Override
    public UserProfile loadUser(String username) throws DataAccessException {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user?username=" + username)
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            Response response = CLIENT.newCall(request).execute();

            if (response.code() == SUCCESS_CODE) {
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONObject userObject = responseBody.getJSONObject("user");

                String password = userObject.getString("password");
                JSONObject info = userObject.optJSONObject("info");
                String displayName = info != null ? info.optString("displayName", "") : "";

                return new UserProfile(userFactory.create(username, password), displayName);
            } else {
                throw new DataAccessException("Failed to load user profile: " + response.message());
            }
        } catch (IOException e) {
            throw new DataAccessException("Error communicating with server: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String username, String password) throws DataAccessException {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user?username=" + username)
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            Response response = CLIENT.newCall(request).execute();

            if (response.code() == SUCCESS_CODE) {
                JSONObject responseBody = new JSONObject(response.body().string());
                String storedPassword = responseBody.getJSONObject("user").getString("password");
                return storedPassword.equals(password);
            } else if (response.code() == 404) {
                return false; // User not found
            } else {
                throw new DataAccessException("Failed to authenticate user: " + response.message());
            }
        } catch (IOException e) {
            throw new DataAccessException("Error communicating with server: " + e.getMessage());
        }
    }

    private void handleResponse(Response response, String errorMessage) throws DataAccessException {
        try {
            if (response.code() != SUCCESS_CODE) {
                JSONObject responseBody = new JSONObject(response.body().string());
                String serverMessage = responseBody.optString("message", response.message());
                throw new DataAccessException(errorMessage + " " + serverMessage);
            }
        } catch (IOException e) {
            throw new DataAccessException("Error parsing response: " + e.getMessage());
        }
    }
}
