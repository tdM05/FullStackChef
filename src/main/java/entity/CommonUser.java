package entity;

import entity.user_profile.User;

import java.util.ArrayList;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {
    private final String name;
    private String password;
    private final Favorite favorite;

    // Delete later because the notes application needs it for now but it will be deleted later so please ignore it
    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.favorite = new CommonFavorite(new ArrayList<>());
    }

    public CommonUser(String name, String password, Favorite favorite) {
        this.name = name;
        this.password = password;
        this.favorite = favorite;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the display name of the user.
     *
     * @return string of the display name.
     */
    @Override
    public String getDisplayName() {
        return "";
    }

    /**
     * Sets the display name of the user.
     *
     * @param displayName
     */
    @Override
    public void setDisplayName(String displayName) {

    }

    @Override
    public Favorite getFavorite() {
        return favorite;
    }
}
