package use_case.search_recipe;

import java.util.List;

/**
 * The response data for the Search Recipe Use Case.
 */
public class SearchRecipeResponseData {
    private final List<SearchRecipeOutputData> recipes;

    /**
     * Constructs SearchRecipeResponseData with the specified list of recipes.
     *
     * @param recipes the list of recipes found
     */
    public SearchRecipeResponseData(List<SearchRecipeOutputData> recipes) {
        this.recipes = recipes;
    }

    /**
     * Retrieves the list of recipes.
     *
     * @return the list of recipes
     */
    public List<SearchRecipeOutputData> getRecipes() {
        return recipes;
    }
}
