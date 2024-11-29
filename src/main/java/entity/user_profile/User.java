package entity.user_profile;

import entity.Favorite;

/**
 * The representation of a password-protected user for our program.
 */
public interface User {
    /**
     * Returns the username of the user.
     *
     * @return string of the username.
     */
    String getName();

    /**
     * Returns the password of associated user.
     *
     * @return the user's password.
     */
    String getPassword();

    /**
     * Sets password with input string.
     *
     */
    void setPassword(String password);

    /**
     * Returns the display name of the user.
     *
     * @return string of the display name.
     */
    String getDisplayName();

    /**
     * Sets the display name of the user.
     *
     * @param displayName
     */
    void setDisplayName(String displayName);

    /**
     * Get the favorite recipes of the user.
     * @return the favorite recipes of the user
     */
    Favorite getFavorite();
}
