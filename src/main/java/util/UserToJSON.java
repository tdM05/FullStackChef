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
            final JSONArray mealJSONArray = new JSONArray();
            for (int mealId : user.getMealIds()) {
                mealJSONArray.put(mealId);
            }
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
