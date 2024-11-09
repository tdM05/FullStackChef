package use_case.search_recipe;

import java.util.List;

public interface SearchRecipeOutputBoundary {
    void prepareSuccessView(List<SearchRecipeOutputData> message);
    void prepareFailView(String errorMessage);
}
