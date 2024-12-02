package interface_adapter.user_profile.logout;

import app.SessionUser;
import use_case.user_profile.logout.LogoutInputBoundary;
import use_case.user_profile.logout.LogoutInputData;

/**
 * The controller for the Logout Use Case.
 */
public class LogoutController {
    private final LogoutInputBoundary interactor;

    /**
     * Constructs a LogoutController with the given use case interactor.
     *
     * @param interactor the use case interactor for logout
     */
    public LogoutController(LogoutInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Logout Use Case for the current session user.
     */
    public void execute() {
        String username = SessionUser.getInstance().getUser().getName();
        interactor.execute(new LogoutInputData(username));
    }
}
