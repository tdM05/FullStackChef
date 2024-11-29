package interface_adapter.user_profile.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.user_profile.change_password.LoggedInState;
import interface_adapter.user_profile.change_password.LoggedInViewModel;
import use_case.user_profile.login.LoginOutputBoundary;
import use_case.user_profile.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(LoginViewModel loginViewModel,
                          LoggedInViewModel loggedInViewModel,
                          ViewManagerModel viewManagerModel){
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to logged in view
        final LoggedInState loggedInState = new getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        ViewManagerState state = new ViewManagerState(loggedInViewModel.getViewName(), null);
        this.viewManagerModel.setState(state);
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error){
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    private static class getState extends LoggedInState {
    }
}
