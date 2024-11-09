package use_case.display_recipe;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;

/**
 * The Interactor for the Display Recipe Use Case.
 * Handles the business logic for displaying a recipe.
 */
public class DisplayRecipeInteractor {

    private final DisplayRecipeOutputBoundary presenter;
    private final DisplayRecipeDataAccessInterface dataAccess;

    /**
     * Constructs a RecipeInteractor with the specified presenter and data access interface.
     *
     * @param presenter  the output boundary to present results
     * @param dataAccess the data access interface for recipe data
     */
    public DisplayRecipeInteractor(DisplayRecipeOutputBoundary presenter, DisplayRecipeDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Handles the request to display a recipe.
     *
     * @param inputData the input data containing the recipe ID
     */
    @Override
    public void displayRecipe(DisplayRecipeInputData inputData) {
        try {
            // Retrieve the recipe using the data access interface
            Recipe recipe = dataAccess.getRecipeById(inputData.getRecipeId());

            // Map the Recipe entity to RecipeOutputData
            DisplayRecipeOutputData outputData = mapRecipeToOutputData(recipe);

            // Invoke the presenter to prepare the success view
            presenter.prepareSuccessView(outputData);
        } catch (IOException | JSONException ex) {
            // In case of any exceptions, prepare the failure view with an error message
            presenter.prepareFailView("Failed to retrieve recipe: " + ex.getMessage());
        }
    }

    /**
     * Maps a Recipe entity to RecipeOutputData.
     *
     * @param recipe the Recipe entity
     * @return the corresponding RecipeOutputData
     */
    private DisplayRecipeOutputData mapRecipeToOutputData(Recipe recipe) {
        return new DisplayRecipeOutputData(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getImage(),
                recipe.getImageType(),
                recipe.getIngredients(),
                recipe.getNutritionalInfo(),
                recipe.getInstructions(),
                recipe.isFavorite()
        );
    }
}
