package interface_adapter.search_recipe;

import entity.Recipe;
import java.util.List;

/**
 * The State for a recipe search.
 * Updated such that recipe search is simply a List of Recipe
 */
public class SearchRecipeState {
    private List<Recipe> recipeSearch;
    private String error;

    public List<Recipe> getRecipe() {
        return recipeSearch;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipeSearch = recipe;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
