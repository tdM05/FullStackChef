package interface_adapter.search_recipe;

import entity.Recipe;
import java.util.List;

/**
 * The State for a recipe search.
 * Updated such that a recipe search is a List of Recipe.
 */
public class SearchRecipeState {
    private List<Recipe> recipeSearch = "";
    private String error;

    public List<Recipe> getRecipes() {
        return recipeSearch;
    }

    public void setRecipe(List<Recipe> recipes) {
        this.recipeSearch = recipe;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
