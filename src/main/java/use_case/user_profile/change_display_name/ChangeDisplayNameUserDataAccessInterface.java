package use_case.user_profile.change_display_name;

import entity.UserProfile;
import use_case.DataAccessException;

public interface ChangeDisplayNameUserDataAccessInterface {
    UserProfile loadUser(String username) throws DataAccessException;

    void saveUser(UserProfile userProfile) throws DataAccessException;

    void changeDisplayName(UserProfile userProfile) throws DataAccessException;
}
