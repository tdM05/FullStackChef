package use_case.change_password;

import entity.CommonUser;

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
        CommonUser commonUser = userDataAccessObject.getUserByUsername(changePasswordInputData.getUsername());

        // Check if the new password is the same as the original password, prepare fail view
        if (commonUser.getPassword().equals(changePasswordInputData.getPassword())){
            userPresenter.prepareFailView("The new password cannot be the same as original password");
            return;
        }

        // Update the commonUser's password
        commonUser.setPassword(changePasswordInputData.getPassword());

        // Save the updated commonUser
        userDataAccessObject.changePassword(commonUser);

        // Prepare the output data and success view
        final ChangePasswordOutputData changePasswordOutputData =
                new ChangePasswordOutputData(commonUser.getName(), false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
