package use_case.display_recipe;

/**
 * The output boundary for the Display Recipe Use Case.
 */
public interface RecipeOutputBoundary {

    /**
     * Prepares the success view for the Display Recipe Use Case.
     *
     * @param response the output data containing recipe details
     */
    void prepareSuccessView(RecipeOutputData response);

    /**
     * Prepares the failure view for the Display Recipe Use Case.
     *
     * @param error the explanation of the failure
     */
    void prepareFailView(String error);
}
