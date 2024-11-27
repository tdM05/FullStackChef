package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import use_case.signup.SignupOutputBoundary;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel){
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView() {
        // On success, switch to the login view
        final SignupState state = signupViewModel.getState();
        state.setSuccess(true);
        signupViewModel.setState(state);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SignupState state = signupViewModel.getState();
        state.setSuccess(false);
        state.setError(errorMessage);
        signupViewModel.setState(state);
        signupViewModel.firePropertyChanged();
    }
}
