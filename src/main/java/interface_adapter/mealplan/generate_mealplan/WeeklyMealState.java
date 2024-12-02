package interface_adapter.mealplan.generate_mealplan;

import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The State for the Weekly Meal.
 */
public class WeeklyMealState {
    private Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan;
    private boolean isLoading;
    private String errorMessage;

    public WeeklyMealState() {
        this.mealPlan = new HashMap<>();
        this.isLoading = false;
        this.errorMessage = null;
    }

    public Map<LocalDate, List<WeeklyMealRecipeDto>> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void clearError() {
        this.errorMessage = null;
    }
}