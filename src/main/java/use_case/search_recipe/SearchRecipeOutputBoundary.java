package use_case.search_recipe;

import java.util.List;

/**
 * The output boundary interface for the Search Recipe Use Case.
 */
public interface SearchRecipeOutputBoundary {
    /**
     * Prepares the success view for the Search Recipe Use Case.
     * @param outputDataList the list of output data
     */
    void prepareSuccessView(List<SearchRecipeOutputData> outputDataList);

    /**
     * Prepares the failure view for the Search Recipe Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
