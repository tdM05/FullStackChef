package use_case.user_profile.change_display_name;

import entity.UserProfile;
import use_case.DataAccessException;

public class ChangeDisplayNameInteractor implements ChangeDisplayNameInputBoundary {
    private final ChangeDisplayNameUserDataAccessInterface dataAccess;
    private final ChangeDisplayNameOutputBoundary outputBoundary;

    public ChangeDisplayNameInteractor(ChangeDisplayNameUserDataAccessInterface dataAccess,
                                       ChangeDisplayNameOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public ChangeDisplayNameOutputData changeDisplayName(ChangeDisplayNameInputData inputData) throws DataAccessException {
        UserProfile userProfile = dataAccess.loadUser(inputData.getUsername());
        if (userProfile == null) {
            throw new DataAccessException("User not found.");
        }

        String newDisplayName = inputData.getNewDisplayName();
        if (newDisplayName == null || newDisplayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be empty.");
        }

        // Update the display name
        userProfile.setDisplayName(newDisplayName);

        // Save the updated user profile back to the DAO
        dataAccess.saveUser(userProfile);

        // Notify the presenter
        ChangeDisplayNameOutputData outputData = new ChangeDisplayNameOutputData(newDisplayName);
        outputBoundary.presentUpdatedDisplayName(outputData);

        return outputData;
    }

}
