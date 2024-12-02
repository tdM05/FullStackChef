package interface_adapter.user_profile.logout;

import app.SessionUser;
import interface_adapter.ViewManagerModel;
import use_case.user_profile.logout.LogoutInputBoundary;
import use_case.user_profile.logout.LogoutInputData;

public class LogoutController {
    private final LogoutInputBoundary interactor;
    private final ViewManagerModel viewManagerModel;

    public LogoutController(LogoutInputBoundary interactor, ViewManagerModel viewManagerModel) {
        this.interactor = interactor;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute() {
        interactor.execute(new LogoutInputData(SessionUser.getInstance().getUser().getName()));
        viewManagerModel.setState("welcomeView");
        viewManagerModel.firePropertyChanged();
    }

}
