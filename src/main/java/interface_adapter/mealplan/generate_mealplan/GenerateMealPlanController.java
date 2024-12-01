package interface_adapter.mealplan.generate_mealplan;

import app.SessionUser;
import entity.User;
import use_case.mealplan.generate_mealplan.GenerateMealPlanInputBoundary;
import use_case.mealplan.generate_mealplan.GenerateMealPlanInputData;

/**
 * The controller for the Generate Meal Plan Use Case.
 */
public class GenerateMealPlanController {

    private final GenerateMealPlanInputBoundary generateMealPlanUseCaseInteractor;

    public GenerateMealPlanController(GenerateMealPlanInputBoundary generateMealPlanUseCaseInteractor) {
        this.generateMealPlanUseCaseInteractor = generateMealPlanUseCaseInteractor;
    }

    public void execute(String diet, String startDate) {
        final GenerateMealPlanInputData inputData = new GenerateMealPlanInputData(diet, startDate);
        User user = SessionUser.getInstance().getUser();
        generateMealPlanUseCaseInteractor.execute(inputData, user);
    }

    public void switchToSearchView() {
        generateMealPlanUseCaseInteractor.switchToSearchView();
    }
}
