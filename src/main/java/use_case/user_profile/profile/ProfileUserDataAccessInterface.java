package use_case.user_profile.profile;

import entity.UserProfile;
import use_case.DataAccessException;

public interface ProfileUserDataAccessInterface {
    UserProfile loadUser(String username) throws DataAccessException;
}
