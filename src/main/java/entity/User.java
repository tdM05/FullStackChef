package entity;

import java.util.List;
import java.util.Map;

/**
 * The representation of a user in our program.
 */
public interface User {
        /**
        * Get the name of the user.
        * @return the name of the user
        */
        String getName();

        /**
        * Get the password of the user.
        * @return the password of the user
        */
        String getPassword();

        /**
        * Set the password of the user.
        * @param password the new password
        */
        void setPassword(String password);

        /**
         * Get the meal ids of the user. The key is the day of the week (in Constants.SOME_DAY format), and
         * the value is a list of meal ids.
         * @return the meal ids of the user
         */
        Map<String, List<Integer>> getMealIds();

        /**
         * Add a meal id to the user.
         * @param mealId the meal id to add
         */
        void setMealIDs(Map<String, List<Integer>> mealId);

        /**
        * Get the favorite recipes of the user.
        * @return the favorite recipes of the user
        */
        Favorite getFavorite();

        void setFavorite(Favorite favorite);
        /**
         * Get the dietary restrictions of the user.
         * @return the dietary restrictions of the user
         */
        DietaryRestriction getDietaryRestrictions();

        void setDietaryRestrictions(DietaryRestriction dietaryRestriction);
}
