package interface_adapter.user_profile.setup;

import java.util.List;

public class SetupState {
    private String displayName;
    private List<String> dietaryRestrictions; // Placeholder for future data

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
}
