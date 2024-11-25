package data_access;

import entity.UserProfile;
import use_case.DataAccessException;

public interface UserDataAccessObject {
    void saveUser(UserProfile user) throws DataAccessException;

    UserProfile loadUser(String username) throws DataAccessException;
}
