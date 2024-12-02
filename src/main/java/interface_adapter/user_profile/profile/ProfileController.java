package interface_adapter.user_profile.profile;

import app.SessionUser;
import interface_adapter.ViewManagerModel;
import use_case.user_profile.profile.ProfileInputBoundary;
import use_case.user_profile.profile.ProfileInputData;

public class ProfileController {
    private final ProfileInputBoundary interactor;
    private final ViewManagerModel viewManagerModel;

    public ProfileController(ProfileInputBoundary interactor, ViewManagerModel viewManagerModel) {
        this.interactor = interactor;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute() {
        try {
            String username = SessionUser.getInstance().getUser().getName();
            interactor.getProfile(new ProfileInputData(username));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch profile: " + e.getMessage(), e);
        } finally {
            viewManagerModel.setState("profileView"); // Navigation logic
        }
    }
}
