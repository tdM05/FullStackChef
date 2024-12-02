package use_case.user_profile.change_display_name;

public class ChangeDisplayNameInputData {
    private final String username;
    private final String newDisplayName;

    public ChangeDisplayNameInputData(String username, String newDisplayName) {
        this.username = username;
        this.newDisplayName = newDisplayName;
    }

    public String getUsername() {
        return username;
    }

    public String getNewDisplayName() {
        return newDisplayName;
    }
}
