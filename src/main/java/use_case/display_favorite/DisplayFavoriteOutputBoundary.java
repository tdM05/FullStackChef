package use_case.display_favorite;

import java.util.List;

/**
 * The output boundary interface for the Display Favorite Use Case.
 */
public interface DisplayFavoriteOutputBoundary {

    /**
     * Prepares the success view for the Display Favorite Use Case.
     * @param outputData the list of output data
     */
    void prepareSuccessView(List<DisplayFavoriteOutputData> outputData);

    /**
     * Prepares the failure view for the Display Favorite Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
