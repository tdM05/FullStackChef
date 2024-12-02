package use_case.mealplan.generate_mealplan;

/**
 * The Input Data for the Generate Meal Plan Use Case.
 * Includes user-specified diet and start date.
 */
public class WeeklyMealInputData {
    private final String diet; // The diet type (e.g., vegetarian, keto, etc.)
    private final String startDate; // The start date for the weekly meal plan (format: YYYY-MM-DD)

    /**
     * Constructs a WeeklyMealInputData object with the specified diet and start date.
     *
     * @param diet      The diet type (optional).
     * @param startDate The start date for the meal plan (required, format: YYYY-MM-DD).
     */
    public WeeklyMealInputData(String diet, String startDate) {
        this.diet = diet;
        this.startDate = startDate;
    }

    /**
     * Gets the diet type.
     *
     * @return The diet type (or null if not specified).
     */
    public String getDiet() {
        return diet;
    }

    /**
     * Gets the start date for the meal plan.
     *
     * @return The start date (format: YYYY-MM-DD).
     */
    public String getStartDate() {
        return startDate;
    }
}

