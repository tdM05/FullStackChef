package use_case.display_recipe;

/**
 * Input boundary for the DisplayRecipe Use Case.
 * Provides an interface for executing the display recipe functionality.
 */
public interface DisplayRecipeInputBoundary {

    /**
     * Executes the DisplayRecipe use case with the given input data.
     *
     * @param inputData The input data containing the recipe ID for displaying.
     */
    void execute(DisplayRecipeInputData inputData);

    /**
     * Switches to the Search View.
     */
    void switchToSearchView();
}
