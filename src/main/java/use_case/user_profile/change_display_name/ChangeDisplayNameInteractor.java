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
        try {
            // Step 1: Load the user profile from the data access interface
            UserProfile userProfile = dataAccess.loadUser(inputData.getUsername());
            if (userProfile == null) {
                throw new DataAccessException("User not found.");
            }

            // Step 2: Update the display name
            String newDisplayName = inputData.getNewDisplayName();
            if (newDisplayName == null || newDisplayName.trim().isEmpty()) {
                throw new IllegalArgumentException("Display name cannot be empty.");
            }

            userProfile.setDisplayName(newDisplayName);

            // Step 3: Persist the updated user profile
            dataAccess.saveUser(userProfile);

            // Step 4: Prepare the output data and notify the presenter
            ChangeDisplayNameOutputData outputData = new ChangeDisplayNameOutputData(newDisplayName);
            outputBoundary.presentUpdatedDisplayName(outputData);

            return outputData;
        } catch (IllegalArgumentException | DataAccessException e) {
            // Log or pass the error to the presenter
            outputBoundary.presentUpdatedDisplayName(
                    new ChangeDisplayNameOutputData("Error: " + e.getMessage())
            );
            throw e;
        }
    }
}
