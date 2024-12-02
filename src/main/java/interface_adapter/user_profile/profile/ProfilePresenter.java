package interface_adapter.user_profile.profile;

import interface_adapter.ViewManagerModel;
import use_case.user_profile.profile.ProfileOutputBoundary;
import use_case.user_profile.profile.ProfileOutputData;

public class ProfilePresenter implements ProfileOutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentProfile(ProfileOutputData outputData) {
        viewManagerModel.setState("profileView");
        viewManagerModel.firePropertyChanged();
    }
}
