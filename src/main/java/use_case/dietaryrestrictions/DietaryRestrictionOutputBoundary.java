package use_case.dietaryrestrictions;

/**
 * The Output Boundary for the Dietary Restrictions Use Case.
 * Defines the methods to present the results to the user interface.
 */
public interface DietaryRestrictionOutputBoundary {
    /**
     * Prepares the success view with the provided DietaryRestrictionResponseData.
     *
     * @param responseData the response data containing confirmation
     */
    void prepareSuccessView(DietaryRestrictionResponseData responseData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message explaining the failure
     */
    void prepareFailView(String errorMessage);
}
