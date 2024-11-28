package interface_adapter.search;

import java.util.List;

import use_case.search.SearchOutputData;

/**
 * The State for a recipe search.
 * Updated such that recipe search is simply a List of Recipe
 */
public class SearchState {
    private List<SearchOutputData> recipeSearch;
    private String error;


    public SearchState() {
    }
    public List<SearchOutputData> getRecipe() {
        return recipeSearch;
    }

    public void setRecipe(List<SearchOutputData> recipe) {
        this.recipeSearch = recipe;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}