package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private boolean success;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }
}
