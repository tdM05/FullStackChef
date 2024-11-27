package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 */
public interface SignupOutputBoundary {
    /**
     * Prepares the success view for the Signup Use Case.
     */
    void prepareSuccessView();

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}