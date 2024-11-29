package interface_adapter.user_profile.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;
    private String repeatPassword = "";
    private String repeatPasswordError;

    // Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Username Error
    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    // Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Password Error
    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    // Repeat Password
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    // Repeat Password Error
    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    @Override
    public String toString(){
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password +'\''
                + ", repeatPassword='" + repeatPassword + '\''
                + '}';
    }
}
