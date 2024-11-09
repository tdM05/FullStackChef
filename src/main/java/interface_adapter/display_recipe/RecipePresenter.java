package interface_adapter.display_recipe;

import interface_adapter.ViewManagerModel;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.display_recipe.DisplayRecipeOutputData;

/**
 * The Presenter for the Display_recipe Use Case.
 */
public class RecipePresenter implements DisplayRecipeOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final RecipeViewModel recipeViewModel;

    public RecipePresenter(ViewManagerModel viewManagerModel, RecipeViewModel recipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.recipeViewModel = recipeViewModel;
    }

    /**
     * Prepares the success view for the DisplayRecipe Use Case.
     *
     * @param response the output data containing recipe details
     */
    @Override
    public void prepareSuccessView(DisplayRecipeOutputData response) {
        // Update the RecipeViewModel with the recipe data
        recipeViewModel.setRecipe(response.getRecipe());
        recipeViewModel.firePropertyChanged("recipe");

        // Optionally, switch the view to display the recipe
        viewManagerModel.setState("DisplayRecipeView");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the DisplayRecipe Use Case.
     *
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        // Update the RecipeViewModel with the error message
        recipeViewModel.setError(error);
        recipeViewModel.firePropertyChanged("error");

        // Optionally, switch the view to display the error
        viewManagerModel.setState("ErrorView");
        viewManagerModel.firePropertyChanged();
    }
}

