package interface_adapter.mealplan.update_meals;

import entity.User;
import use_case.mealplan.update_meals.UpdateMealsInputBoundary;

public class UpdateMealsController {
    private final UpdateMealsInputBoundary updateMealsInputBoundary;

    public UpdateMealsController(UpdateMealsInputBoundary updateMealsInputBoundary) {
        this.updateMealsInputBoundary = updateMealsInputBoundary;
    }
    public void execute(User user) {
        updateMealsInputBoundary.execute(user);
    }
}
