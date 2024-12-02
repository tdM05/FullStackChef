package interface_adapter.user_profile.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.login.LoginState;
import interface_adapter.user_profile.login.LoginViewModel;
import use_case.user_profile.signup.SignupOutputBoundary;
import use_case.user_profile.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, navigate to the SetupView
        viewManagerModel.setState("setupView");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // Update the SignupState with the error message
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        // Navigate to LoginView explicitly if requested
        viewManagerModel.setState("loginView");
        viewManagerModel.firePropertyChanged();
    }
}
