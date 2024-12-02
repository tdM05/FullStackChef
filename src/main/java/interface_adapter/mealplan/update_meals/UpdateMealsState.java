package interface_adapter.mealplan.update_meals;

import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateMealsState {
    private Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan;

    public Map<LocalDate, List<WeeklyMealRecipeDto>> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(Map<LocalDate, List<WeeklyMealRecipeDto>> newMealPlan) {
        mealPlan = newMealPlan;
    }
}
