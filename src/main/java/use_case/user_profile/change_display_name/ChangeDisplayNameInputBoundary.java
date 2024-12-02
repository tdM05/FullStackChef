package use_case.user_profile.change_display_name;

import use_case.DataAccessException;

public interface ChangeDisplayNameInputBoundary {
    ChangeDisplayNameOutputData changeDisplayName(ChangeDisplayNameInputData inputData) throws DataAccessException;
}
