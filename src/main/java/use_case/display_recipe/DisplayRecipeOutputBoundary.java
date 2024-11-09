package use_case.display_recipe;

/**
 * The Output Boundary for the Display Recipe Use Case.
 * Defines the methods to present the results to the user interface.
 */
public interface DisplayRecipeOutputBoundary {
    /**
     * Prepares the success view with the provided DisplayRecipeOutputData.
     *
     * @param outputData the output data containing recipe details
     */
    void prepareSuccessView(DisplayRecipeOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message explaining the failure
     */
    void prepareFailView(String errorMessage);
}
