package use_case.search;

import java.util.List;

/**
 * The output data interface for the Search Use Case.
 */
public interface SearchOutputBoundary {

    /**
     * Prepares the success view for the Search Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(List<SearchOutputData> outputData);

    /**
     * Prepares the failure view for the Search Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
