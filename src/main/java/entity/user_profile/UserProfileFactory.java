package entity.user_profile;

/**
 * Factory implementation for creating UserProfile instances.
 */
public class UserProfileFactory implements UserFactory {
    @Override
    public User create(String username, String password) {
        return new RegularUser(username, password); // RegularUser creation
    }

    @Override
    public User createGuest() {
        return new GuestUser(); // Create a guest user directly
    }
}