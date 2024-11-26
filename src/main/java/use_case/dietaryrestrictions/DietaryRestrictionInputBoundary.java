package use_case.dietaryrestrictions;

/**
 * The Input Boundary for the Dietary Restrictions Use Case.
 * Defines the method to execute the use case.
 */
public interface DietaryRestrictionInputBoundary {
    /**
     * Executes the use case to set dietary restrictions.
     *
     * @param requestData the input data containing dietary restrictions
     */
    void execute(DietaryRestrictionRequestData requestData);
}
