package use_case.favorite;

import entity.User;

import java.util.List;

/**
 * The interface of the DAO for the Favorite Recipe Use Case.
 */
public interface FavoriteDataAccessInterface {

    /**
     * Returns the list of favorite recipe IDs for the given user.
     * @param user the user whose favorite recipes are to be retrieved
     * @return the list of favorite recipe IDs for the given user
     */
    List<Integer> getFavorites(User user);

    /**
     * Updates the system to record the user's favorite recipes.
     *
     * @param user the user whose favorite recipes are to be saved
     */
    void saveFavorites(User user);

}
