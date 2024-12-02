package use_case.user_profile.signup;

import use_case.DataAccessException;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData) throws DataAccessException;

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}
