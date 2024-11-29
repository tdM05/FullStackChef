package use_case.user_profile.change_password;

/**
 * The input data for the Change Password Use Case.
 */
public class ChangePasswordInputData {
    private final String username;
    private final String newPassword;
    private final String repeatPassword;

    public ChangePasswordInputData(String username, String newPassword, String repeatPassword) {
        this.username = username;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
