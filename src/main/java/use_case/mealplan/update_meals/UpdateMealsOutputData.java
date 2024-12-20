package use_case.mealplan.update_meals;

import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateMealsOutputData {
    private final Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan;

    /**
     * Constructs a GenerateMealPlanOutputData object.
     *
     * @param mealPlan A map where the key is the date and the value is a list of recipe DTOs for that date.
     */
    public UpdateMealsOutputData(Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    /**
     * Returns the updated meal plan.
     *
     * @return A map of dates to lists of recipe DTOs.
     */
    public Map<LocalDate, List<WeeklyMealRecipeDto>> getMealPlan() {
        return mealPlan;
    }
}
