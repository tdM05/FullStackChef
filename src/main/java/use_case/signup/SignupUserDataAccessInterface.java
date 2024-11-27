package use_case.signup;

import entity.User;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the commonUser.
     * @param username the username to save.
     * @param password the password to save.
     */
    void save(String username, String password);
}
