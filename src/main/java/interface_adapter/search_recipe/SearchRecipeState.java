package interface_adapter.search_recipe;

import java.util.List;

import use_case.search_recipe.SearchRecipeOutputData;

/**
 * The State for a recipe search.
 * Updated such that recipe search is simply a List of Recipe
 */
public class SearchRecipeState {
    private List<SearchRecipeOutputData> recipeSearch;
    private String error;

    public List<SearchRecipeOutputData> getRecipe() {
        return recipeSearch;
    }

    public void setRecipe(List<SearchRecipeOutputData> recipe) {
        this.recipeSearch = recipe;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
