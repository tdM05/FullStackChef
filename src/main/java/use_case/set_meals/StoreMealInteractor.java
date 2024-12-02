package use_case.set_meals;

import data_access.Constants;
import data_access.UserProfile.ProfileException;
import entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.UserToJSON;

import java.util.List;
import java.util.Map;

/**
 * The Store Meal Interactor.
 */
public class StoreMealInteractor implements StoreMealInputBoundary {
    private final StoreMealDataAccessInterface dataAccess;

    public StoreMealInteractor(StoreMealDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the Store Meal Use Case.
     * At a high level, this turns the user into a JSONObject which is the body of the
     * api call but before sending this body to the dao, it adds the mealId to the body.
     * @param inputData contains the recipe id to store
     * @param user is the current session user who wants to store the meal
     */
    @Override
    public void execute(StoreMealInputData inputData, User user) {
        final JSONObject userJSON = UserToJSON.userToJSON(user);

        final JSONObject info = userJSON.getJSONObject(Constants.INFO);
        final JSONObject mealIdsJSON = info.getJSONObject(Constants.MEAL_IDS);

        final Map<String, List<Integer>> mealIds = inputData.getMealIds();
        // put all mealIds into the mealIdsJSON
        for (Map.Entry<String, List<Integer>> entry : mealIds.entrySet()) {
            final JSONArray dayMeals = new JSONArray();
            for (int meal : entry.getValue()) {
                dayMeals.put(meal);
            }
            mealIdsJSON.put(entry.getKey(), dayMeals);
        }
        // now userJSON has the mealIds added to it
        // Send this new user info to the api
        dataAccess.setInfo(userJSON);

        // set user meals locally
        final Map<String, List<Integer>> newMeals = JSONMealsToMap(mealIdsJSON);
        // Add this meal locally to the user
        user.setMealIDs(newMeals);
    }

    /**
     * Converts a JSONArray to a List of Integers.
     * @param meals The JSONArray to convert.
     * @return The List of Integers.
     * @throws ProfileException if the JSONArray cannot be converted.
     */
    List<Integer> JSONArrayToList(JSONArray meals) throws ProfileException {
        final List<Integer> mealList = new java.util.ArrayList<>();
        for (int i = 0; i < meals.length(); i++) {
            try {
                mealList.add(meals.getInt(i));
            }
            catch (JSONException ex) {
                throw new ProfileException("Failed to store meal.");
            }
        }
        return mealList;
    }

    Map<String, List<Integer>> JSONMealsToMap(JSONObject meals) {
        final Map<String, List<Integer>> mealMap = new java.util.HashMap<>();
        try {
            for (String day : Constants.DAYS_OF_WEEK) {
                final JSONArray dayMeals = meals.getJSONArray(day);
                mealMap.put(day, JSONArrayToList(dayMeals));
            }
        }
        catch (JSONException ex) {
            throw new IllegalArgumentException("Failed to convert JSON to Map.");
        }
        return mealMap;
    }
}
