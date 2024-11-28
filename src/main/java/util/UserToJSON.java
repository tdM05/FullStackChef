package util;

import data_access.Constants;
import data_access.UserProfile.ProfileException;
import entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts a User object to a JSON object.
 */
public class UserToJSON {
    /**
     * Converts a User object to a JSON object.
     * @param user The user to convert.
     * @return The JSON object.
     * @throws ProfileException if the user cannot be converted.
     */
    public static JSONObject userToJSON(User user) throws ProfileException {
        final JSONObject body = new JSONObject();
        try {
            body.put("username", user.getName());
            body.put("password", user.getPassword());

            // Now we deal with the info key
            final JSONObject info = new JSONObject();
            // create the meals to add to info
            final JSONObject mealJSONArray = new JSONObject();

            // add monday meals
            final JSONArray mondayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.MONDAY)) {
                mondayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.MONDAY, mondayMeals);

            // add tuesday meals
            final JSONArray tuesdayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.TUESDAY)) {
                tuesdayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.TUESDAY, tuesdayMeals);
            // add wednesday meals
            final JSONArray wednesdayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.WEDNESDAY)) {
                wednesdayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.WEDNESDAY, wednesdayMeals);

            // add thursday meals
            final JSONArray thursdayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.THURSDAY)) {
                thursdayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.THURSDAY, thursdayMeals);

            // add friday meals
            final JSONArray fridayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.FRIDAY)) {
                fridayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.FRIDAY, fridayMeals);

            // add saturday meals
            final JSONArray saturdayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.SATURDAY)) {
                saturdayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.SATURDAY, saturdayMeals);

            // add sunday meals
            final JSONArray sundayMeals = new JSONArray();
            for (int mealId : user.getMealIds().get(Constants.SUNDAY)) {
                sundayMeals.put(mealId);
            }
            mealJSONArray.put(Constants.SUNDAY, sundayMeals);

            info.put(Constants.MEAL_ID, mealJSONArray);

            // create the favorites to add to info
            final JSONArray favorite = new JSONArray();
            for (int favId : user.getFavorite().getFavoriteRecipes()) {
                favorite.put(favId);
            }
            info.put(Constants.FAVORITE, favorite);
            // create the dietary restrictions to add to info
            final JSONArray dietaryRestrictions = new JSONArray();
            for (String restriction : user.getDietaryRestrictions().getDiets()) {
                dietaryRestrictions.put(restriction);
            }
            info.put(Constants.DIETARY_RESTRICTIONS, dietaryRestrictions);

            body.put("info", info);
            return body;
        }
        catch (JSONException ex) {
            throw new ProfileException("Failed to convert user to JSON.");
        }
    }
}
