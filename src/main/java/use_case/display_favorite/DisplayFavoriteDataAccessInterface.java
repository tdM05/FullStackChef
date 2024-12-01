package use_case.display_favorite;

import entity.Recipe;
import entity.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the DisplayFavorite use case
 */
public interface DisplayFavoriteDataAccessInterface {

    /**
     * Returns the list of favorite recipe IDs for the given user.
     *
     * @param user the user whose favorite recipes are to be retrieved
     * @return the list of favorite recipe IDs for the given user
     */
    List<Integer> getFavorites(User user);

    /**
     * Returns the list of recipes for the given recipe IDs.
     *
     * @param recipeId the list of recipe IDs to be retrieved
     * @return the list of recipes for the given recipe IDs
     */
    List<Recipe> getRecipes(String recipeId) throws IOException, JSONException;

}
