package entity.user_profile;

import entity.Favorite;

/**
 * The representation of a user profile in our program.
 */
public class UserProfile implements User {
    private final User user;
    private String displayName;

    public UserProfile(User user, String displayName) {
        this.user = user;
        this.displayName = displayName;
    }

    public User getUser() {
        return user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Favorite getFavorite() {
        return user.getFavorite();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        user.setPassword(password);
    }
}
