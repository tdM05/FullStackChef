package interface_adapter.user_profile.change_password;

import interface_adapter.ViewModel;

public class LoggedInViewModel extends ViewModel<LoggedInState> {
    public LoggedInViewModel(){
        super("logged in");
        setState(new LoggedInState());
    }
}
