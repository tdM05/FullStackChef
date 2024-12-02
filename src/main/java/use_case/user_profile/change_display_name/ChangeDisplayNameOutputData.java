package use_case.user_profile.change_display_name;

public class ChangeDisplayNameOutputData {
    private final String updatedDisplayName;

    public ChangeDisplayNameOutputData(String updatedDisplayName) {
        this.updatedDisplayName = updatedDisplayName;
    }

    public String getUpdatedDisplayName() {
        return updatedDisplayName;
    }
}
