package use_case.favorite_recipe;

import entity.user_profile.User;

import java.util.List;

/**
 * The interface of the DAO for the Favorite Recipe Use Case.
 */
public interface FavoriteRecipeDataAccessInterface {
    /**
     * Returns the list of favorite recipe IDs for the given user.
     * @param user the user whose favorite recipes are to be retrieved
     * @return the list of favorite recipe IDs for the given user
     * @throws FavoriteException if there is an error retrieving the favorite recipes
     */
    List<Integer> getFavorites(User user) throws FavoriteException;

    /**
     * Updates the system to record the user's favorite recipes.
     *
     * @param user the user whose favorite recipes are to be saved
     * @throws FavoriteException if there is an error saving the favorite recipes
     */
    void saveFavorites(User user) throws FavoriteException;

}
