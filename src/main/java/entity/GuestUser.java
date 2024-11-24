package entity;

/**
 * The representation of a guest user in our program.
 */
public class GuestUser implements User {
    private final String username = "Guest";
    private final String password = "";

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        // Guest is not allowed to change password
    }

    @Override
    public String getDisplayName() {
        return "Guest"; // Display name for guest
    }

    @Override
    public void setDisplayName(String displayName) {
        // Guests don't have a customizable display name
    }
}