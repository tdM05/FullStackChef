package use_case.display_history;

import entity.Recipe;
import entity.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * The data access interface for the Display History Use Case.
 */
public interface DisplayHistoryDataAccessInterface {

    /**
     * Returns the list of recipe IDs in the user's history.
     *
     * @param user the user whose history is to be retrieved
     * @return the list of recipe IDs in the user's history
     */
    List<Integer> getHistory(User user);

    /**
     * Returns the list of recipes for the given recipe IDs.
     *
     * @param recipeId the list of recipe IDs to be retrieved
     * @return the list of recipes for the given recipe IDs
     */
    List<Recipe> getRecipes(String recipeId) throws IOException, JSONException;
}
