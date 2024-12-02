package use_case.user_profile.change_password;

import entity.User;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;

    public ChangePasswordInteractor (ChangePasswordUserDataAccessInterface changePasswordUserDataAccessInterface,
                                     ChangePasswordOutputBoundary changePasswordOutputBoundary){
        this.userDataAccessObject = changePasswordUserDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData){
        // Retrieve existing commonUser
        User user = userDataAccessObject.getUserByUsername(changePasswordInputData.getUsername());

        // Check if the new password is the same as the original password, prepare fail view
        if (user.getPassword().equals(changePasswordInputData.getNewPassword())){
            userPresenter.prepareFailView("The new password cannot be the same as original password");
            return;
        }

        // Update the commonUser's password
        user.setPassword(changePasswordInputData.getNewPassword());

        // Save the updated commonUser
        userDataAccessObject.changePassword(user);

        // Prepare the output data and success view
        final ChangePasswordOutputData changePasswordOutputData =
                new ChangePasswordOutputData(user.getName(), false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
