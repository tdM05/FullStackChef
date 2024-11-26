package use_case.dietaryrestrictions;

/**
 * The Response Data for the Dietary Restrictions Use Case.
 * Contains confirmation of successful setting of dietary restrictions.
 */
public class DietaryRestrictionResponseData {
    private final String message;

    /**
     * Constructs DietaryRestrictionResponseData with the specified message.
     *
     * @param message the confirmation message
     */
    public DietaryRestrictionResponseData(String message) {
        this.message = message;
    }

    /**
     * Retrieves the confirmation message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
