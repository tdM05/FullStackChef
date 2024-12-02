package interface_adapter.user_profile.profile;

import use_case.user_profile.profile.ProfileInputBoundary;
import use_case.user_profile.profile.ProfileInputData;

public class ProfileController {
    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void fetchProfile(String username) {
        ProfileInputData inputData = new ProfileInputData(username);
        interactor.getProfile(inputData);
    }
}
