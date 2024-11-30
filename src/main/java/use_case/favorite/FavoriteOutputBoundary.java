package use_case.favorite;

/**
 * The output data interface for the Favorite Recipe Use Case.
 */
public interface FavoriteOutputBoundary {

    /**
     * Prepares the success view for the Favorite Recipe Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FavoriteOutputData outputData);

    /**
     * Prepares the failure view for the Favorite Recipe Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
