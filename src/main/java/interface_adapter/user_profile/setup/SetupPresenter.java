package interface_adapter.user_profile.setup;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;

public class SetupPresenter {
    private final ViewManagerModel viewManagerModel;
    private final UserProfileViewModel userProfileViewModel;

    public SetupPresenter(UserProfileViewModel userProfileViewModel, ViewManagerModel viewManagerModel) {
        this.userProfileViewModel = userProfileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void updateDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            displayName = userProfileViewModel.getState().getName(); // Default to username
        }
        userProfileViewModel.getState().setDisplayName(displayName);
        userProfileViewModel.firePropertyChanged();
    }

    public void navigateToSearchView() {
        viewManagerModel.setState("searchView");
        viewManagerModel.firePropertyChanged();
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}
