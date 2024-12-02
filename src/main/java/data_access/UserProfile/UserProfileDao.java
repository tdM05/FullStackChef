package data_access.UserProfile;

import data_access.Constants;
import entity.*;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;
import use_case.set_meals.StoreMealDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Data Access Object for the User Profile.
 */
public class UserProfileDao implements StoreMealDataAccessInterface {
    private static final String STATUS_CODE_LABEL = Constants.STATUS_CODE_LABEL;
    private static final int SUCCESS_CODE = Constants.SUCCESS_CODE;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String INFO = "info";
    private final OkHttpClient client;

    public UserProfileDao() {
        client = new OkHttpClient();
    }

    /**
     * Set the meals of the user.
     * Precondition: The user has logged in.
     * @param userJSON This is what you get when you call UserToJSON.userToJSON(user)
     */
    @Override
    public void setInfo(JSONObject userJSON) {
        // http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo
        final String url = "http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo";
        final JSONObject jsonBody = userJSON;

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
     * This will set the meals of the user to null if the key doesn't exist, and it will set it correctly
     * if the key does exist.
     *
     * @param username The username of the user.
     * @return The user.
     */
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

            final User user = new CommonUser(name, password);
            final Map<String, List<Integer>> mealIds = new HashMap<>();
            // We check if it has meals
            if (info.has(Constants.MEAL_IDS)) {
                // first we should reset user meals if the MEAL_ID key is not a json object
                if (!(info.get(Constants.MEAL_IDS) instanceof JSONObject)) {
                    user.setMealIDs(new HashMap<>());
                    return user;
                }
                final JSONObject mealsObject = info.getJSONObject(Constants.MEAL_IDS);

                // deal with monday
                mealIds.put(Constants.MONDAY, new ArrayList<>());
                if (mealsObject.has(Constants.MONDAY)) {
                    final JSONArray mondayMeals = mealsObject.getJSONArray(Constants.MONDAY);
                    for (int i = 0; i < mondayMeals.length(); i++) {
                        mealIds.get(Constants.MONDAY).add(mondayMeals.getInt(i));
                    }
                }
                // deal with tuesday
                mealIds.put(Constants.TUESDAY, new ArrayList<>());
                if (mealsObject.has(Constants.TUESDAY)) {
                    final JSONArray tuesdayMeals = mealsObject.getJSONArray(Constants.TUESDAY);
                    for (int i = 0; i < tuesdayMeals.length(); i++) {
                        mealIds.get(Constants.TUESDAY).add(tuesdayMeals.getInt(i));
                    }
                }
                // deal with wednesday
                mealIds.put(Constants.WEDNESDAY, new ArrayList<>());
                if (mealsObject.has(Constants.WEDNESDAY)) {
                    final JSONArray wednesdayMeals = mealsObject.getJSONArray(Constants.WEDNESDAY);
                    for (int i = 0; i < wednesdayMeals.length(); i++) {
                        mealIds.get(Constants.WEDNESDAY).add(wednesdayMeals.getInt(i));
                    }
                }
                // deal with thursday
                mealIds.put(Constants.THURSDAY, new ArrayList<>());
                if (mealsObject.has(Constants.THURSDAY)) {
                    final JSONArray thursdayMeals = mealsObject.getJSONArray(Constants.THURSDAY);
                    for (int i = 0; i < thursdayMeals.length(); i++) {
                        mealIds.get(Constants.THURSDAY).add(thursdayMeals.getInt(i));
                    }
                }
                // deal with friday
                mealIds.put(Constants.FRIDAY, new ArrayList<>());
                if (mealsObject.has(Constants.FRIDAY)) {
                    final JSONArray fridayMeals = mealsObject.getJSONArray(Constants.FRIDAY);
                    for (int i = 0; i < fridayMeals.length(); i++) {
                        mealIds.get(Constants.FRIDAY).add(fridayMeals.getInt(i));
                    }
                }
                // deal with saturday
                mealIds.put(Constants.SATURDAY, new ArrayList<>());
                if (mealsObject.has(Constants.SATURDAY)) {
                    final JSONArray saturdayMeals = mealsObject.getJSONArray(Constants.SATURDAY);
                    for (int i = 0; i < saturdayMeals.length(); i++) {
                        mealIds.get(Constants.SATURDAY).add(saturdayMeals.getInt(i));
                    }
                }
                // deal with sunday
                mealIds.put(Constants.SUNDAY, new ArrayList<>());
                if (mealsObject.has(Constants.SUNDAY)) {
                    final JSONArray sundayMeals = mealsObject.getJSONArray(Constants.SUNDAY);
                    for (int i = 0; i < sundayMeals.length(); i++) {
                        mealIds.get(Constants.SUNDAY).add(sundayMeals.getInt(i));
                    }
                }
                // if the user has meals, we get and return a user with meal
                user.setMealIDs(mealIds);
            }

            // we should check if they have favorites and dietary restrictions
            // and do something similar to the if statement above
            user.setFavorite(new CommonFavorite(new ArrayList<>()));
            user.setDietaryRestrictions(new CommonDietaryRestriction(new ArrayList<>()));
            return user;
        }
        catch (IOException | JSONException exception) {
            throw new ProfileException("Failed to get user.");
        }
    }

    /**
     * Save the user.
     * This will save the user to the database and is used when registering a new user.
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
