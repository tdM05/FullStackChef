package use_case.mealplan.generate_mealplan;

/**
 * The Output Boundary for the Generate Meal Plan Use Case.
 * Defines the methods to present the results to the user interface.
 */
public interface GenerateMealPlanOutputBoundary {
    /**
     * Prepares the success view when the meal plan is generated successfully.
     *
     * @param outputData The output data containing the generated meal plan.
     */
    void prepareSuccessView(GenerateMealPlanOutputData outputData);

    /**
     * Prepares the failure view when the meal plan generation fails.
     *
     * @param errorMessage A message describing the error that occurred.
     */
    void prepareFailView(String errorMessage);
}

