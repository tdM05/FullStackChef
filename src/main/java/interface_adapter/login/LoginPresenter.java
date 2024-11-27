package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(LoginViewModel loginViewModel,
                          ViewManagerModel viewManagerModel){
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView() {
        final LoginState state = loginViewModel.getState();
        state.setSuccess(true);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState state = loginViewModel.getState();
        state.setSuccess(false);
        state.setError(error);
        loginViewModel.firePropertyChanged();
    }
}
