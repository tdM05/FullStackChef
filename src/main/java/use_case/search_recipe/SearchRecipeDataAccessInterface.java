package use_case.search_recipe;

import entity.Recipe;
import java.util.List;

/**
 * Interface for the NoteDAO. It consists of methods for
 * both loading and saving a note.
 */
public interface SearchRecipeDataAccessInterface {

    /**
     * Searches for recipes that match the given input string.
     * @param input the input string to search for
     * @return a list of recipes that match the input string
     * @throws DataAccessException if the recipes cannot be retrieved for any reason
     */
    List<Recipe> searchRecipe(String input) throws DataAccessException;

    /**
     * Retrieves a recipe by its ID.
     * @param id the ID of the recipe to retrieve
     * @return the recipe with the given ID
     * @throws DataAccessException if the recipe cannot be retrieved for any reason
     */
    Recipe getRecipeById(int id) throws DataAccessException;
}
