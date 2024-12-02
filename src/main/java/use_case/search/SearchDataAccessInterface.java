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
     *
     * @param ingredients the list of ingredients to search for
     * @param number      the number of recipes to return
     * @return the list of recipes that can be made with the given ingredients
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    List<Recipe> getRecipes(List<String> ingredients, int number) throws IOException, JSONException;

    /**
     * Returns the list of recipes that can be made with the given ingredients and dietary filters.
     *
     * @param ingredientsList the list of ingredient names
     * @param dietaryFilters  the list of dietary restrictions
     * @param number          the number of recipes to retrieve
     * @return the list of recipes matching the search criteria and dietary restrictions
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    List<Recipe> getRecipes(List<String> ingredientsList, List<String> dietaryFilters, int number) throws IOException, JSONException;
}
