package use_case.search_recipe;

import java.util.List;

public interface SearchRecipeOutputBoundary {
    void prepareSuccessView(List<SearchRecipeOutputData> outputDataList);
    void prepareFailView(String errorMessage);
}
