package util;

import data_access.Constants;
import data_access.UserProfile.ProfileException;
import entity.DietaryRestriction;
import entity.Favorite;
import entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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

            // add meals for each day of the week
            for (String day : Constants.DAYS_OF_WEEK) {
                final JSONArray dayMeals = new JSONArray();
                final Map<String, List<Integer>> userMealIds = user.getMealIds();
                if (userMealIds.containsKey(day)) {
                    for (int mealId : userMealIds.get(day)) {
                        dayMeals.put(mealId);
                    }
                }
                mealJSONArray.put(day, dayMeals);
            }

            info.put(Constants.MEAL_IDS, mealJSONArray);

            // create the favorites to add to info
            final JSONArray favorite = new JSONArray();
            final Favorite userFavorite = user.getFavorite();
            if (userFavorite != null) {
                for (int favId : userFavorite.getFavoriteRecipes()) {
                    favorite.put(favId);
                }
            }
            info.put(Constants.FAVORITE, favorite);

            // create the dietary restrictions to add to info
            final JSONArray dietaryRestrictions = new JSONArray();
            DietaryRestriction userDietaryRestrictions = user.getDietaryRestrictions();
            if (userDietaryRestrictions != null) {
                for (String restriction : userDietaryRestrictions.getDiets()) {
                    dietaryRestrictions.put(restriction);
                }
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
