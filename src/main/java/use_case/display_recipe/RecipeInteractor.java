package use_case.display_recipe;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;

/**
 * The Interactor for the Display Recipe Use Case.
 * Handles the business logic for displaying a recipe.
 */
public class RecipeInteractor implements RecipeInputBoundary {

    private final RecipeOutputBoundary presenter;
    private final RecipeDataAccessInterface dataAccess;

    /**
     * Constructs a RecipeInteractor with the specified presenter and data access interface.
     *
     * @param presenter  the output boundary to present results
     * @param dataAccess the data access interface for recipe data
     */
    public RecipeInteractor(RecipeOutputBoundary presenter, RecipeDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Handles the request to display a recipe.
     *
     * @param inputData the input data containing the recipe ID
     */
    @Override
    public void displayRecipe(RecipeInputData inputData) {
        try {
            // Retrieve the recipe using the data access interface
            Recipe recipe = dataAccess.getRecipeById(inputData.getId());

            // Map the Recipe entity to RecipeOutputData
            RecipeOutputData outputData = mapRecipeToOutputData(recipe);

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
    private RecipeOutputData mapRecipeToOutputData(Recipe recipe) {
        return new RecipeOutputData(
                recipe.getId(),
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
