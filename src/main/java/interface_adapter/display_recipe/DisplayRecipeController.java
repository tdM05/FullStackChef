package interface_adapter.display_recipe;

import use_case.display_recipe.DisplayRecipeInputBoundary;
import use_case.display_recipe.DisplayRecipeInputData;

/**
 * The controller for the DisplayRecipe Use Case.
 */
public class DisplayRecipeController {

    private final DisplayRecipeInputBoundary displayRecipeUseCaseInteractor;

    public DisplayRecipeController(DisplayRecipeInputBoundary displayRecipeUseCaseInteractor) {
        this.displayRecipeUseCaseInteractor = displayRecipeUseCaseInteractor;
    }

    /**
     * Executes the DisplayRecipe Use Case.
     * @param RecipeId the unique identifier of the recipe to display
     */
    public void execute(int RecipeId) {
        // 1. Instantiate the `DisplayRecipeInputData`, which should contain the recipe ID.
        final DisplayRecipeInputData inputData = new DisplayRecipeInputData(RecipeId);
        // 2. Tell the Interactor to execute.
        displayRecipeUseCaseInteractor.execute(inputData);
    }
}
