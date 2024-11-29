package interface_adapter.user_profile.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.change_password.LoggedInState;
import interface_adapter.user_profile.change_password.LoggedInViewModel;
import interface_adapter.user_profile.login.LoginState;
import interface_adapter.user_profile.login.LoginViewModel;
import use_case.user_profile.logout.LogoutOutputBoundary;
import use_case.user_profile.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {
    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;

    public LogoutPresenter(LoggedInViewModel loggedInViewModel,
                           ViewManagerModel viewManagerModel,
                           LoginViewModel loginViewModel){
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response){
        // Set the username in the LoggedInState to the empty string.
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername("");
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        // Set username and password in login view to empty string.
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");
        loginState.setPassword("");
        this.loginViewModel.setState(loginState);
        this.loginViewModel.firePropertyChanged();

        // Switch to login view.
//        this.viewManagerModel.setState(loginViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error){
        // Logout use case can't fail.
    }
}
