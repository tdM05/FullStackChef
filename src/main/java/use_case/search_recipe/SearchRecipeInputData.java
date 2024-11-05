package use_case.search_recipe;

public interface SearchRecipeInputData {
    /**
     * Returns the search query.
     * This query is in the format "ingredient 1, ingredient 2, ingredient 3, ...".
     * @return the search query.
     */
    String searchQuery();
}
