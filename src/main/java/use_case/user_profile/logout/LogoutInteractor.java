package use_case.user_profile.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutUserDataAccessInterface userDataAccessObject;
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary){
        this.userDataAccessObject = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute(LogoutInputData logoutInputData) {
        userDataAccessObject.setCurrentUsername(null);
        LogoutOutputData outputData = new LogoutOutputData(logoutInputData.getUsername(), false);
        logoutPresenter.prepareSuccessView(outputData);
    }
}
