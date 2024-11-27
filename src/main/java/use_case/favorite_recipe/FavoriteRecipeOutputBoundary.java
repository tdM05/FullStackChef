package use_case.favorite_recipe;

/**
 * The output data interface for the Favorite Recipe Use Case.
 */
public interface FavoriteRecipeOutputBoundary {
    /**
     * Prepares the success view for the Favorite Recipe Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(FavoriteRecipeOutputData outputData);

    /**
     * Prepares the failure view for the Favorite Recipe Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
