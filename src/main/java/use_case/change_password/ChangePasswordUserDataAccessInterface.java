package use_case.change_password;

import entity.CommonUser;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {
    /**
     * Updates the system to record this commonUser's password.
     * @param commonUser the commonUser whose password is to be updated
     */
    void changePassword(CommonUser commonUser);

    /**
     * Retrieves a user by their username.
     * @param username the username of the user to retrieve
     * @return the user with the given username
     */
    CommonUser getUserByUsername(String username);
}
