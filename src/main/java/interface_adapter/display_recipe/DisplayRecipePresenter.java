package interface_adapter.display_recipe;

import interface_adapter.ViewManagerModel;
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
        // Create a new DisplayRecipeState and populate it with data from outputData
        final DisplayRecipeState displayRecipeState = new DisplayRecipeState();

        displayRecipeState.setRecipeName(outputData.getTitle());
        displayRecipeState.setIngredients(outputData.getIngredients().toString());
        displayRecipeState.setInstructions(outputData.getInstructions().toString());

        // Update the DisplayRecipeViewModel with the new state
        displayRecipeViewModel.setState(displayRecipeState);

        // Update viewManager with the new view name
        viewManager.setState(displayRecipeViewModel.getViewName());
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message explaining the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final DisplayRecipeState displayRecipeState = displayRecipeViewModel.getState();
        displayRecipeState.setDisplayError(errorMessage);
        displayRecipeViewModel.firePropertyChanged();
    }
}
