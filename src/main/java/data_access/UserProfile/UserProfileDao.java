package data_access.UserProfile;

import app.SessionManager;
import data_access.Constants;
import entity.CommonUser;
import entity.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.store_meal.StoreMealDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object for the User Profile.
 */
public class UserProfileDao implements StoreMealDataAccessInterface, LoginUserDataAccessInterface,
        SignupUserDataAccessInterface {
    private static final String API_KEY = Constants.API_KEY;
    private static final String STATUS_CODE_LABEL = Constants.STATUS_CODE_LABEL;
    private static final int SUCCESS_CODE = Constants.SUCCESS_CODE;

    private final OkHttpClient client;

    public UserProfileDao() {
        client = new OkHttpClient();
    }

    /**
     * Set the meals of the user.
     *
     * Precondition: The user has logged in.
     * @param mealIds The meal ids.
     */
    @Override
    public void setMeals(List<Integer> mealIds) {
        // http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo
        final String url = "http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo";
        final JSONObject jsonBody = new JSONObject();

        // create the info object
        final JSONObject info = new JSONObject();
        final JSONArray meals = new JSONArray(mealIds);

        // Get the current user
        final SessionManager session = SessionManager.getInstance();
        final User user = session.getCurrentUser();
        try {
            jsonBody.put("username", user.getName());
            jsonBody.put("password", user.getPassword());
            info.put(Constants.MEAL_ID, meals);
            jsonBody.put("info", info);
        }
        catch (JSONException exception) {
            throw new ProfileException("Failed to set meals.");
        }
        final RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        final Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .addHeader("token", "GBBHZDA1bqRXSKp2KuKN6BqdSFFoYahy")
                .addHeader("Content-Type", "application/json")
                .build();

        // here we actually call the api
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int code = responseBody.getInt(STATUS_CODE_LABEL);
            final String message = responseBody.getString(Constants.MESSAGE);
            if (code != SUCCESS_CODE || !message.equals("User info modified successfully.")) {
                throw new ProfileException(message);
            }
        }
        catch (IOException | JSONException exception) {
            throw new ProfileException("Failed to set meals.");
        };
    }

    // LoginUserDataAccessInterface
    @Override
    public boolean existsByName(String username) {
        final String USER_EXISTS = "User exists";
        // http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=test_user
        final String url = String.format("http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s", username);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int code = responseBody.getInt(STATUS_CODE_LABEL);
            final String message = responseBody.getString(Constants.MESSAGE);
            if (code != SUCCESS_CODE) {
                return false;
            }
            return message.equals(USER_EXISTS);
        }
        catch (IOException | JSONException exception) {
            throw new ProfileException("Failed to check if user exists.");
        }
    }

    /**
     * Get the user by username.
     * This will get the meals of the user to null if the key doesn't exist, and it will set it correctly
     * if the key does exist.
     *
     * @param username The username of the user.
     * @return The user.
     */
    @Override
    public User getUser(String username) {
        //http://vm003.teach.cs.toronto.edu:20112/user?username=test_user
        final String url = String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int code = responseBody.getInt(STATUS_CODE_LABEL);
            final String message = responseBody.getString(Constants.MESSAGE);
            if (code != SUCCESS_CODE || !message.equals("User retrieved successfully")) {
                throw new ProfileException(message);
            }
            final JSONObject userJson = responseBody.getJSONObject("user");
            final String name = userJson.getString("username");
            final String password = userJson.getString("password");
            final JSONObject info = userJson.getJSONObject("info");
            // We check if it has meals
            if (info.has(Constants.MEAL_ID)) {
                final JSONArray mealsObject = info.getJSONArray(Constants.MEAL_ID);
                final List<Integer> meals = new ArrayList<>();
                for (int i = 0; i < mealsObject.length(); i++) {
                    if (!(mealsObject.get(i) instanceof Integer)) {
                        return new CommonUser(name, password);
                    }
                    else {
                        meals.add((Integer) mealsObject.get(i));
                    }
                }
                // if the user has meals, we get and return a user with meal
                final User user = new CommonUser(name, password);
                user.setMealIDs(meals);
                return user;
            }
            return new CommonUser(name, password);
        }
        catch (IOException | JSONException exception) {
            throw new ProfileException("Failed to get user.");
        }
    }

    /**
     * Save the user.
     * This will save the user to the database.
     *
     * @param username The user to save.
     * @param password The password of the user.
     *
     * @throws ProfileException when the user fails to save.
     */
    public void save(String username, String password) throws ProfileException {
        // http://vm003.teach.cs.toronto.edu:20112/user
        final String url = "http://vm003.teach.cs.toronto.edu:20112/user";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
        }
        catch (JSONException exception) {
            throw new ProfileException("Failed to save user.");
        }
        final RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("token", "GBBHZDA1bqRXSKp2KuKN6BqdSFFoYahy")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int code = responseBody.getInt(STATUS_CODE_LABEL);
            final String message = responseBody.getString(Constants.MESSAGE);
            if (code != SUCCESS_CODE || !message.equals("User created successfully.")) {
                throw new ProfileException(message);
            }
        }
        catch (IOException | JSONException exception) {
            throw new ProfileException("Failed to save user.");
        }
    }
}
