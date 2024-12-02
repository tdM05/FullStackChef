package interface_adapter.user_profile.change_display_name;

import use_case.DataAccessException;
import use_case.user_profile.change_display_name.ChangeDisplayNameInputBoundary;
import use_case.user_profile.change_display_name.ChangeDisplayNameInputData;

public class ChangeDisplayNameController {
    private final ChangeDisplayNameInputBoundary interactor;

    public ChangeDisplayNameController(ChangeDisplayNameInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void updateDisplayName(String username, String newDisplayName) throws DataAccessException {
        ChangeDisplayNameInputData inputData = new ChangeDisplayNameInputData(username, newDisplayName);
        interactor.changeDisplayName(inputData);
    }
}
