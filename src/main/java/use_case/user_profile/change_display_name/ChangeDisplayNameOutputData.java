package use_case.user_profile.change_display_name;

public class ChangeDisplayNameOutputData {
    private final String newDisplayName;
    private final String errorMessage;

    public ChangeDisplayNameOutputData(String newDisplayName) {
        this(newDisplayName, null);
    }

    public ChangeDisplayNameOutputData(String newDisplayName, String errorMessage) {
        this.newDisplayName = newDisplayName;
        this.errorMessage = errorMessage;
    }

    public String getNewDisplayName() {
        return newDisplayName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null;
    }
}
