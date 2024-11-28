package interface_adapter.search;

import java.util.List;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * The Presenter for the Search Recipe Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    /**
     * Prepares the success view for the SearchRecipe related Use Cases.
     *
     * @param outputDataList the output data
     */
    @Override
    public void prepareSuccessView(List<SearchOutputData> outputDataList) {
        // On success, update the view model with the search results.
        searchViewModel.setRecipes(outputDataList);
        searchViewModel.firePropertyChanged("recipes");

        // Optionally, switch the view to show the search results.
//        viewManagerModel.setState("SearchResultsView");
//        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the SearchRecipe related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // On failure, update the view model with the error message.
        searchViewModel.setError(errorMessage);
        searchViewModel.firePropertyChanged("error");
    }
}
