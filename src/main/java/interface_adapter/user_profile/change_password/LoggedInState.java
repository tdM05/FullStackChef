package interface_adapter.user_profile.change_password;

/**
 * The State information representing the logged-in user.
 */
public class LoggedInState {
    private String username = "";
    private String password = "";
    private String passwordError;

    /** COPY CONSTRUCTOR
     * When you need to create a new instance that is a copy of an existing instance, preserving its state.
     * This can be handy for undo/redo functionality, cloning objects,
     * or when you need to pass objects around without modifying the original.
     * Can be implemented if user can edit their profile
     */
    public LoggedInState (LoggedInState copy){
        this.username = copy.username;
        this.password = copy.password;
        this.passwordError = copy.passwordError;
    }

    // Default constructor. Have to be explicit bc of copy constructor
    public LoggedInState(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
}
