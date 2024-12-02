package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password);
    }

    /**
     * Creates a guest user.
     *
     * @return the guest user
     */
    @Override
    public User createGuest() {
        return new CommonUser("guest", "guest");
    }
}
