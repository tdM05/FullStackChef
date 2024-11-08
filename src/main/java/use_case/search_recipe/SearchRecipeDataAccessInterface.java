package use_case.search_recipe;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the NoteDAO. It consists of methods for
 * both loading and saving a note.
 */
public interface SearchRecipeDataAccessInterface {

    /**
     * Returns a list of recipes based on the query.
     *
     * @param ingredientsString is a list of ingredients.
     *                          Notice that ingredients should not be a list of Ingredients because this is the
     *                          data access object, meaning Ingredient objects should get depend on this to get nutritional
     *                          info. This should not depend on ingredients. In other words, Ingredients should not be
     *                          the one handing fetching nutritional info through the api. This is the job
     *                          of this class.
     * @param number            is the number of recipes to return.
     * @return a list of recipes based on the query.
     * @throws IOException   if an I/O or JSON parsing error occurs.
     * @throws JSONException if a JSON parsing error occurs.
     */
    List<Recipe> getRecipesByIngredient(List<String> ingredientsString, int number)
            throws IOException, JSONException;
}