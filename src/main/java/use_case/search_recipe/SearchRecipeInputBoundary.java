package use_case.search_recipe;

/**
 * Input Boundary for actions which are related to searching for recipes.
 */
public interface SearchRecipeInputBoundary {
    /**
     * Executes the Search Recipe use case.
     * @param searchRecipeInputData the input data
     */
    void execute(SearchRecipeInputData searchRecipeInputData);
}
