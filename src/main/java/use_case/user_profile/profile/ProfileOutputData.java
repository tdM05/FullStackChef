package use_case.user_profile.profile;

import entity.UserProfile;

public class ProfileOutputData {
    private final UserProfile userProfile;

    public ProfileOutputData(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
