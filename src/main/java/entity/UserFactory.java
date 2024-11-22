package entity;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new CommonUser.
     * @param username the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    CommonUser create(String username, String password);
}
