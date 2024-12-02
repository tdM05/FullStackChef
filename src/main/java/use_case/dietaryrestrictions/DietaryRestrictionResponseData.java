package use_case.dietaryrestrictions;

import java.util.List;

/**
 * The Response Data for the Dietary Restrictions Use Case.
 * Contains confirmation of successful setting of dietary restrictions and the list of dietary restrictions.
 */
public class DietaryRestrictionResponseData {
    private final String message;
    private final List<String> dietaryRestrictions;

    /**
     * Constructs DietaryRestrictionResponseData with the specified message and dietary restrictions.
     *
     * @param message              the confirmation message
     * @param dietaryRestrictions  the list of dietary restrictions
     */
    public DietaryRestrictionResponseData(String message, List<String> dietaryRestrictions) {
        this.message = message;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    /**
     * Retrieves the confirmation message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the list of dietary restrictions.
     *
     * @return the list of dietary restrictions
     */
    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }
}
