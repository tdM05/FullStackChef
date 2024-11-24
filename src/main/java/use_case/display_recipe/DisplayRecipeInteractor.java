package use_case.display_recipe;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;

/**
 * The Interactor for the Display Recipe Use Case.
 * Handles the business logic for displaying a recipe.
 */
public class DisplayRecipeInteractor implements DisplayRecipeInputBoundary {

    private final DisplayRecipeOutputBoundary presenter;
    private final DisplayRecipeDataAccessInterface dataAccess;

    /**
     * Constructs a DisplayRecipeInteractor with the specified presenter and data access interface.
     *
     * @param presenter  the output boundary to present results
     * @param dataAccess the data access interface for recipe data
     */
    public DisplayRecipeInteractor(DisplayRecipeOutputBoundary presenter, DisplayRecipeDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the request to display a recipe.
     *
     * @param inputData the input data containing the recipe ID
     */
    @Override
    public void execute(DisplayRecipeInputData inputData) {
        System.out.println("DisplayRecipeInteractor executing with recipeId: " + inputData.getRecipeId());
        try {
            System.out.println("Interactor: Retrieving recipe with ID " + inputData.getRecipeId());
            Recipe recipe = dataAccess.getRecipeById(inputData.getRecipeId());
            System.out.println("Interactor: Recipe retrieved successfully");

            DisplayRecipeOutputData outputData = mapRecipeToOutputData(recipe);
            presenter.prepareSuccessView(outputData);
            System.out.println("Interactor: Presenter invoked for success view");
        } catch (IOException | JSONException ex) {
            System.out.println("Interactor: Exception occurred - " + ex.getMessage());
            presenter.prepareFailView("Failed to retrieve recipe: " + ex.getMessage());
        }
    }

    /**
     * Maps a Recipe entity to DisplayRecipeOutputData.
     *
     * @param recipe the Recipe entity
     * @return the corresponding DisplayRecipeOutputData
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
