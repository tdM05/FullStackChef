package data_access.user_profile;

import entity.User;
import entity.UserProfile;
import entity.UserFactory;
import okhttp3.*;
import org.json.JSONObject;
import use_case.DataAccessException;
import use_case.user_profile.change_password.ChangePasswordInputBoundary;
import use_case.user_profile.change_password.ChangePasswordInputData;
import use_case.user_profile.change_password.ChangePasswordUserDataAccessInterface;
import use_case.user_profile.login.LoginUserDataAccessInterface;
import use_case.user_profile.signup.SignupUserDataAccessInterface;

import java.io.IOException;

public class UserAuthDataAccessObject implements
        UserDataAccessObject,
        SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface, ChangePasswordInputBoundary {
    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112";
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder().build();
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final int SUCCESS_CODE = 200;

    private final UserFactory userFactory;
    private String currentUsername;

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

    /**
     * Checks if the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    @Override
    public boolean existsByName(String username) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/user?username=" + username)
                    .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                    .build();

            Response response = CLIENT.newCall(request).execute();
            return response.code() == SUCCESS_CODE;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Saves the commonUser.
     *
     * @param user the User to save
     */
    @Override
    public void save(User user) throws DataAccessException {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("username", user.getName());
            requestBody.put("password", user.getPassword());

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

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user with the given username
     */
    @Override
    public User get(String username) {
        try {
            UserProfile userProfile = loadUser(username);
            return userProfile.getUser();
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    /**
     * Updates the system to record this commonUser's password.
     *
     * @param user the commonUser whose password is to be updated
     */
    @Override
    public void changePassword(User user) {
        try {
            save(user);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the given username
     */
    @Override
    public User getUserByUsername(String username) {
        return get(username);
    }

    /**
     * Execute the Change Password Use Case.
     *
     * @param changePasswordInputData the input data for this use case
     */
    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        User user = getUserByUsername(changePasswordInputData.getUsername());

        if (user != null && !user.getPassword().equals(changePasswordInputData.getNewPassword())) {
            user.setPassword(changePasswordInputData.getNewPassword());
            changePassword(user);
        }
    }
}
