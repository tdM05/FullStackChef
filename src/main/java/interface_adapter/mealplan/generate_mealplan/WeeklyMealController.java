package interface_adapter.mealplan.generate_mealplan;

import app.SessionUser;
import entity.User;
import use_case.mealplan.generate_mealplan.WeeklyMealInputBoundary;
import use_case.mealplan.generate_mealplan.WeeklyMealInputData;

/**
 * The controller for the Weekly Meal Use Case.
 */
public class WeeklyMealController {

    private final WeeklyMealInputBoundary weeklyMealUseCaseInteractor;

    public WeeklyMealController(WeeklyMealInputBoundary weeklyMealUseCaseInteractor) {
        this.weeklyMealUseCaseInteractor = weeklyMealUseCaseInteractor;
    }

    public void execute(String diet, String startDate) {
        final WeeklyMealInputData inputData = new WeeklyMealInputData(diet, startDate);
        User user = SessionUser.getInstance().getUser();
        weeklyMealUseCaseInteractor.execute(inputData, user);
    }

    public void switchToSearchView() {
        weeklyMealUseCaseInteractor.switchToSearchView();
    }
}
