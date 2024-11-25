package use_case.change_password;

import entity.User;
import use_case.DataAccessException;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {
    /**
     * Updates the system to record this user's password.
     * @param user the user whose password is to be updated
     */
    void changePassword(User user);

    /**
     * Retrieves a user by their username.
     * @param username the username of the user to retrieve
     * @return the user with the given username
     */
    User getUserByUsername(String username);
}
