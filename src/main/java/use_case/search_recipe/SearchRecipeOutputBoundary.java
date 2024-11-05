package use_case.search_recipe;

public interface SearchRecipeOutputBoundary {
    void prepareSuccessView(SearchRecipeOutputData message);
    void prepareFailView(String errorMessage);
}
