package interface_adapter.user_profile.change_display_name;

import interface_adapter.ViewManagerModel;
import use_case.user_profile.change_display_name.ChangeDisplayNameOutputBoundary;
import use_case.user_profile.change_display_name.ChangeDisplayNameOutputData;

public class ChangeDisplayNamePresenter implements ChangeDisplayNameOutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public ChangeDisplayNamePresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentUpdatedDisplayName(ChangeDisplayNameOutputData outputData) {
        if (outputData.hasError()) {
            System.err.println("Failed to update display name: " + outputData.getErrorMessage());
        } else {
            System.out.println("Successfully updated display name to: " + outputData.getNewDisplayName());
        }
    }
}
