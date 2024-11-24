package interface_adapter;

import entity.RegularUser;
import entity.UserProfile;

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