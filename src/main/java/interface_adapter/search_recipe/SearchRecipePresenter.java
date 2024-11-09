package interface_adapter.search_recipe;

import java.util.List;

import interface_adapter.ViewManagerModel;
import use_case.search_recipe.SearchRecipeOutputBoundary;
import use_case.search_recipe.SearchRecipeOutputData;

/**
 * The Presenter for the Search Recipe Use Case.
 */
public class SearchRecipePresenter implements SearchRecipeOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SearchRecipeViewModel searchRecipeViewModel;

    public SearchRecipePresenter(ViewManagerModel viewManagerModel, SearchRecipeViewModel searchRecipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchRecipeViewModel = searchRecipeViewModel;
    }

    /**
     * Prepares the success view for the SearchRecipe related Use Cases.
     *
     * @param outputDataList the output data
     */
    @Override
    public void prepareSuccessView(List<SearchRecipeOutputData> outputDataList) {
        // On success, update the view model with the search results.
        searchRecipeViewModel.setRecipes(outputDataList);
        searchRecipeViewModel.firePropertyChanged("recipes");

        // Optionally, switch the view to show the search results.
        viewManagerModel.setState("SearchResultsView");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the SearchRecipe related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // On failure, update the view model with the error message.
        searchRecipeViewModel.setError(errorMessage);
        searchRecipeViewModel.firePropertyChanged("error");
    }
}
