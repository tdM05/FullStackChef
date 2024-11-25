package interface_adapter.change_password;

import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;

public class ChangePasswordController {
    private final ChangePasswordInputBoundary interactor;

    public ChangePasswordController(ChangePasswordInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String newPassword, String repeatPassword) {
        ChangePasswordInputData inputData = new ChangePasswordInputData(username, newPassword, repeatPassword);
        interactor.execute(inputData);
    }
}
