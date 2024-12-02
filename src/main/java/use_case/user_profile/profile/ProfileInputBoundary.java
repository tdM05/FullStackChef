package use_case.user_profile.profile;

import use_case.DataAccessException;

public interface ProfileInputBoundary {
    ProfileOutputData getProfile(ProfileInputData inputData) throws DataAccessException;
}
