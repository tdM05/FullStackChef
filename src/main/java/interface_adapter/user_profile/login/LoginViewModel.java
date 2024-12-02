package interface_adapter.user_profile.login;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel extends ViewModel<LoginState> {
    public LoginViewModel(){
        super("logInView");
        setState(new LoginState());
    }

    private String currentView;

    public String getCurrentView() {
        return currentView;
    }

    public void setCurrentView(String view) {
        this.currentView = view;
        firePropertyChanged("currentView");
    }
}
