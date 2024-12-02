package use_case.display_history;

import java.util.List;

/**
 * The output boundary interface for the Display History Use Case.
 */
public interface DisplayHistoryOutputBoundary {

    /**
     * Prepares the success view for the Display History Use Case.
     * @param outputData the list of output data
     */
    void prepareSuccessView(List<DisplayHistoryOutputData> outputData);

    /**
     * Prepares the failure view for the Display History Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Prepares the "switch to SearchView" Use Case.
     */
    void switchToSearchView();


}

