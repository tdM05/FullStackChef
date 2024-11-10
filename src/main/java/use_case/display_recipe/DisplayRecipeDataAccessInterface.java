package use_case.display_recipe;

import entity.Recipe;
import java.io.IOException;
import org.json.JSONException;

/**
 * The Data Access Interface for the Display Recipe Use Case.
 * Defines the methods required to access Recipe data for display purposes.
 */
public interface DisplayRecipeDataAccessInterface {

    /**
     * Retrieves detailed information of a Recipe by its unique identifier.
     *
     * @param id the unique identifier of the recipe
     * @return the Recipe entity corresponding to the given id
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    Recipe getRecipeById(int id) throws IOException, JSONException;
}
