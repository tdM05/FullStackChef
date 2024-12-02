package use_case.user_profile.change_display_name;

import entity.UserProfile;
import use_case.DataAccessException;
import use_case.user_profile.change_display_name.ChangeDisplayNameUserDataAccessInterface;

public class ChangeDisplayNameInteractor implements ChangeDisplayNameInputBoundary {
    private final ChangeDisplayNameUserDataAccessInterface dataAccess;
    private final ChangeDisplayNameOutputBoundary outputBoundary;

    public ChangeDisplayNameInteractor(ChangeDisplayNameUserDataAccessInterface dataAccess, ChangeDisplayNameOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public ChangeDisplayNameOutputData changeDisplayName(ChangeDisplayNameInputData inputData) throws DataAccessException {
        UserProfile userProfile = dataAccess.loadUser(inputData.getUsername());
        userProfile.setDisplayName(inputData.getNewDisplayName());
        dataAccess.saveUser(userProfile);

        ChangeDisplayNameOutputData outputData = new ChangeDisplayNameOutputData(inputData.getNewDisplayName());
        outputBoundary.presentUpdatedDisplayName(outputData);
        return outputData;
    }
}
