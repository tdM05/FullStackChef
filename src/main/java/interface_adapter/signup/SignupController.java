package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {
    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }
    /**
     * Executes the Signup Use Case.
     * @param username the username to sign up
     * @param password the password
     * @param repeatPassword the repeated password
     */
    public void execute(String username, String password, String repeatPassword) {
        final SignupInputData signupInputData = new SignupInputData(username, password, repeatPassword);
        userSignupUseCaseInteractor.execute(signupInputData);
    }
}
