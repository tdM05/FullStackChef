package use_case.search;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * The interface of the DAO for the Search Use Case.
 */
public interface SearchDataAccessInterface {

    /**
     * Returns the list of recipes that can be made with the given ingredients.
     * @param ingredients the list of ingredients to search for
     * @param number the number of recipes to return
     * @return the list of recipes that can be made with the given ingredients
     */
    List<Recipe> getRecipes(List<String> ingredients, int number) throws IOException, JSONException;

    /**
     * Returns the list of recipes that can be made with the given ingredients and diet filter.
     * @param ingredients the list of ingredients to search for
     * @param number the number of recipes to return
     * @param dietFilter the list of diet filters to apply
     * @return the list of recipes that can be made with the given ingredients and diet filter
     * List<Recipe> getRecipes(List<String> ingredients, int number, List<String> dietFilter);
     */

}
