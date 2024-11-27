package entity;

import java.util.List;

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
         * Get the meal ids of the user.
         * @return the meal ids of the user
         */
        List<Integer> getMealIds();

        /**
         * Add a meal id to the user.
         * @param mealId the meal id to add
         */
        void setMealIDs(List<Integer> mealId);

        /**
        * Get the favorite recipes of the user.
        * @return the favorite recipes of the user
        */
        Favorite getFavorite();
}
