package interface_adapter.display_recipe;

import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.display_recipe.DisplayRecipeOutputData;

/**
 * The Presenter for the Display Recipe Use Case.
 * Implements the DisplayRecipeOutputBoundary to prepare data for the view.
 */
public class DisplayRecipePresenter implements DisplayRecipeOutputBoundary {

    private final ViewManagerModel viewManager;
    private final DisplayRecipeViewModel displayRecipeViewModel;

    /**
     * Constructs a DisplayRecipePresenter with the specified ViewManagerModel and DisplayRecipeViewModel.
     *
     * @param viewManager          the view manager model to handle view switching
     * @param displayRecipeViewModel the view model to update with recipe data
     */
    public DisplayRecipePresenter(ViewManagerModel viewManager, DisplayRecipeViewModel displayRecipeViewModel) {
        this.viewManager = viewManager;
        this.displayRecipeViewModel = displayRecipeViewModel;
    }

    /**
     * Prepares the success view with the provided DisplayRecipeOutputData.
     *
     * @param outputData the output data containing recipe details
     */
    @Override
    public void prepareSuccessView(DisplayRecipeOutputData outputData) {
        // Update the DisplayRecipeViewModel with the recipe details
        displayRecipeViewModel.setRecipeId(outputData.getRecipeId());
        displayRecipeViewModel.setTitle(outputData.getTitle());
        displayRecipeViewModel.setImage(outputData.getImage());
        displayRecipeViewModel.setImageType(outputData.getImageType());
        displayRecipeViewModel.setIngredients(outputData.getIngredients());
        displayRecipeViewModel.setNutritionalInfo(outputData.getNutritionalInfo());
        displayRecipeViewModel.setInstructions(outputData.getInstructions());
        displayRecipeViewModel.setFavorite(outputData.isFavorite());

        // Instruct the ViewManager to switch to the RecipeDetailView
        viewManager.switchTo("RecipeDetailView");
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message explaining the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // Update the DisplayRecipeViewModel with the error message
        displayRecipeViewModel.setErrorMessage(errorMessage);

        // Instruct the ViewManager to switch to the ErrorView
        viewManager.switchTo("ErrorView");
    }
}
