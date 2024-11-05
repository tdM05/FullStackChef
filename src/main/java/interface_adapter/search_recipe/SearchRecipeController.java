package interface_adapter.search_recipe;

import use_case.search_recipe.SearchRecipeInputBoundary;
import use_case.search_recipe.SearchRecipeInputData;

/**
 * Controller for the Search Recipe Use Case.
 */
public class SearchRecipeController {

    private final SearchRecipeInputBoundary searchRecipeInteractor;

    public SearchRecipeController(SearchRecipeInputBoundary searchRecipeInteractor) {
        this.searchRecipeInteractor = searchRecipeInteractor;
    }

    /**
     * Executes the search for recipes that match the given input string.
     * @param input the input string to search for
     */
    public void searchRecipe(String input) {
        SearchRecipeInputData inputData = new SearchRecipeInputData() {
            @Override
            public String searchQuery() {
                return input;
            }
        };
        searchRecipeInteractor.execute(inputData);
    }
}
