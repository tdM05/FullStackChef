package interface_adapter.search_recipe;

/**
 * The State for a recipe search.
 * <p>For this example, a recipe search is simply a string.</p>
 */
public class SearchRecipeState {
    private String recipeSearch = "";
    private String error;

    public String getRecipe() {
        return recipeSearch;
    }

    public void setRecipe(String recipe) {
        this.recipeSearch = recipe;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
