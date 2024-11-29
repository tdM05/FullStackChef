package interface_adapter.user_profile;

import entity.user_profile.RegularUser;
import entity.user_profile.UserProfile;
import interface_adapter.ViewModel;

public class UserProfileViewModel extends ViewModel<UserProfile> {

    public UserProfileViewModel() {
        super("user profile");
        setState(new UserProfile(new RegularUser("", ""), ""));
    }

    public void updateUserProfile(String username, String password, String displayName) {
        UserProfile userProfile = getState();
        userProfile.getUser().setPassword(password);
        userProfile.setDisplayName(displayName);
        setState(userProfile);
        firePropertyChanged();
    }
}