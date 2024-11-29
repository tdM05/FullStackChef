package use_case.mealplan.update_meals;

import entity.User;

public interface UpdateMealsInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(User user);
}
