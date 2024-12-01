package use_case.mealplan.generate_mealplan;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The Output Data for the Weekly Meal Use Case.
 */
public class WeeklyMealOutputData {
    private final Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan;

    /**
     * Constructs a WeeklyMealOutputData object.
     *
     * @param mealPlan A map where the key is the date and the value is a list of recipe DTOs for that date.
     */
    public WeeklyMealOutputData(Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    /**
     * Returns the generated meal plan.
     *
     * @return A map of dates to lists of recipe DTOs.
     */
    public Map<LocalDate, List<WeeklyMealRecipeDto>> getMealPlan() {
        return mealPlan;
    }
}
