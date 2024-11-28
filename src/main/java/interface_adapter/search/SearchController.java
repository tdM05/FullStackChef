package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for the Search Recipe Use Case.
 */
public class SearchController {
    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the search for recipes that match the given input string.
     * @param query the input string to search for
     */
    public void execute(String query) {
        SearchInputData searchInputData = new SearchInputData(query);
        searchInteractor.execute(searchInputData);
    }
}
