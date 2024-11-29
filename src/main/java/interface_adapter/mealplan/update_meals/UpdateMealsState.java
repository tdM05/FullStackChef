package interface_adapter.mealplan.update_meals;

import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateMealsState {
    private Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan;

    public Map<LocalDate, List<GenerateMealPlanRecipeDto>> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(Map<LocalDate, List<GenerateMealPlanRecipeDto>> newMealPlan) {
        mealPlan = newMealPlan;
    }
}
