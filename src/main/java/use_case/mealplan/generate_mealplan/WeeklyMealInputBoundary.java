package use_case.mealplan.generate_mealplan;

import entity.User;
import entity.WeeklyMeal;

/**
 * Input boundary for the Generate Meal Plan Use Case.
 * This interface is implemented by the interactor to process input data.
 */
public interface WeeklyMealInputBoundary {
    /**
     * Executes the meal plan generation use case.
     *
     * @param inputData The input data containing the diet and start date.
     * @param user The user for whom the meal plan is being generated.
     */
    void execute(WeeklyMealInputData inputData, User user);

    void switchToSearchView();
}
