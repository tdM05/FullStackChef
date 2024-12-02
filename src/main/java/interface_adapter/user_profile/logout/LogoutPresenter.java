package interface_adapter.user_profile.logout;

import interface_adapter.ViewManagerModel;
import use_case.user_profile.logout.LogoutOutputBoundary;
import use_case.user_profile.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        // Handle view navigation on successful logout
        viewManagerModel.setState("welcomeView");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Login Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // Can't fail
    }
}
