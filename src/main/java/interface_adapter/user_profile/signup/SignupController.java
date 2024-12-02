package interface_adapter.user_profile.signup;

import use_case.DataAccessException;
import use_case.user_profile.signup.SignupInputBoundary;
import use_case.user_profile.signup.SignupInputData;
import interface_adapter.ViewManagerModel;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {
    private final SignupInputBoundary userSignupUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor, ViewManagerModel viewManagerModel) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the Signup Use Case.
     * @param username the username to sign up
     * @param password the password
     * @param repeatPassword the password repeated
     * @throws DataAccessException if there is a data access error
     */
    public void execute(String username, String password, String repeatPassword) throws DataAccessException {
        // Create the input data object for signup
        final SignupInputData signupInputData = new SignupInputData(username, password, repeatPassword);

        // Execute the signup logic
        userSignupUseCaseInteractor.execute(signupInputData);

        // After successful signup, navigate to SetupView
        navigateToSetupView();
    }

    /**
     * Navigates to the SetupView.
     */
    private void navigateToSetupView() {
        // Transition to the SetupView state in the ViewManager
        viewManagerModel.setState("setupView");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView(){
        userSignupUseCaseInteractor.switchToLoginView();
    }
}
