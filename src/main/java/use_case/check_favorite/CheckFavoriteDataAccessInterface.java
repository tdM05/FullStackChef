package use_case.check_favorite;

import entity.User;

import java.util.List;

/**
 * The data access interface for the Check Favorite Use Case.
 */
public interface CheckFavoriteDataAccessInterface {

    /**
     * Returns the list of favorite recipe IDs for the given user.
     * @param user the user whose favorite recipes are to be retrieved
     * @return the list of favorite recipe IDs for the given user
     */
    List<Integer> getFavorites(User user);

}
