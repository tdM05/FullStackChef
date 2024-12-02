package use_case.user_profile.profile;

import entity.UserProfile;
import use_case.DataAccessException;

public class ProfileInteractor implements ProfileInputBoundary {
    private final ProfileUserDataAccessInterface dataAccess;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(ProfileUserDataAccessInterface dataAccess, ProfileOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public ProfileOutputData getProfile(ProfileInputData inputData) throws DataAccessException {
        UserProfile userProfile = dataAccess.loadUser(inputData.getUsername());
        ProfileOutputData outputData = new ProfileOutputData(userProfile);
        presenter.presentProfile(outputData);
        return outputData;
    }
}
