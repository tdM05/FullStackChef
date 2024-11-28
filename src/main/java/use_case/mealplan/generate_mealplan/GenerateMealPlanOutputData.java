package use_case.mealplan.generate_mealplan;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The Output Data for the Generate Meal Plan Use Case.
 */
public class GenerateMealPlanOutputData {
    private final Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan;

    /**
     * Constructs a GenerateMealPlanOutputData object.
     *
     * @param mealPlan A map where the key is the date and the value is a list of recipe DTOs for that date.
     */
    public GenerateMealPlanOutputData(Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    /**
     * Returns the generated meal plan.
     *
     * @return A map of dates to lists of recipe DTOs.
     */
    public Map<LocalDate, List<GenerateMealPlanRecipeDto>> getMealPlan() {
        return mealPlan;
    }
}
