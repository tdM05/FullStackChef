package use_case.change_password;

import entity.User;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface userDataAccessObject,
                                    ChangePasswordOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(ChangePasswordInputData inputData) {
        User user = userDataAccessObject.getUserByUsername(inputData.getUsername());

        // Check if new password matches repeated password
        if (!inputData.getNewPassword().equals(inputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords do not match.");
            return;
        }

        // Check if the new password is the same as the old password
        if (user.getPassword().equals(inputData.getNewPassword())) {
            userPresenter.prepareFailView("The new password cannot be the same as the old password.");
            return;
        }

        // Update the user's password
        user.setPassword(inputData.getNewPassword());
        userDataAccessObject.changePassword(user);

        // Prepare success view
        ChangePasswordOutputData outputData = new ChangePasswordOutputData(user.getName(), false);
        userPresenter.prepareSuccessView(outputData);
    }
}
