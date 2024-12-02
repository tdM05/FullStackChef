package interface_adapter.user_profile.setup;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;

public class SetupController {
    private final SetupPresenter setupPresenter;

    public SetupController(SetupPresenter setupPresenter) {
        this.setupPresenter = setupPresenter;
    }

    /**
     * Updates the display name and navigates to the search view.
     */
    public void updateDisplayNameAndNavigate(String displayName) {
        setupPresenter.updateDisplayName(displayName);
        setupPresenter.navigateToSearchView();
    }

    /**
     * Skips the setup, using the username as the display name, and navigates to the search view.
     */
    public void skipSetup() {
        setupPresenter.updateDisplayName(null); // Passing null will default to username
        setupPresenter.navigateToSearchView();
    }

    public ViewManagerModel getViewManagerModel() {
        return setupPresenter.getViewManagerModel();
    }
}
