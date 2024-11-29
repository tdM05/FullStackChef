package interface_adapter.dietaryrestrictions;

/**
 * The state for the CommonDietaryRestriction ViewModel.
 */
public class DietaryRestrictionState {
    private String message;

    /**
     * Retrieves the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
