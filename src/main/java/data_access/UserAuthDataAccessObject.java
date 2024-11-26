package data_access;

import entity.UserProfile;
import entity.UserFactory;
import org.json.JSONObject;
import okhttp3.*;
import use_case.DataAccessException;

import java.io.IOException;

public class UserAuthDataAccessObject implements UserDataAccessObject {
    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112";
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private final UserFactory userFactory;

    public UserAuthDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public void saveUser(UserProfile user) throws DataAccessException {
        JSONObject payload = new JSONObject();
        payload.put("username", user.getName());
        payload.put("password", user.getPassword());

        JSONObject info = new JSONObject();
        info.put("displayName", user.getDisplayName());

        payload.put("info", info);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + "/modifyUserInfo")
                .put(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new DataAccessException("Failed to save user profile: " + response.message());
            }
        } catch (IOException e) {
            throw new DataAccessException("Error communicating with server: " + e.getMessage());
        }
    }

    @Override
    public UserProfile loadUser(String username) throws DataAccessException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/user?username=" + username)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.code() == 200) {
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONObject userObject = responseBody.getJSONObject("user");
                String password = userObject.getString("password");
                String displayName = userObject.getJSONObject("info").optString("displayName", "");

                return new UserProfile(userFactory.create(username, password), displayName);
            } else {
                throw new DataAccessException("Failed to load user profile: " + response.message());
            }
        } catch (IOException e) {
            throw new DataAccessException("Error communicating with server: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String username, String password) throws DataAccessException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/user?username=" + username)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.code() == 200) {
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
}
