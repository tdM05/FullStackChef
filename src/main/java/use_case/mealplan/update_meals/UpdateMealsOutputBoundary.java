package use_case.mealplan.update_meals;

public interface UpdateMealsOutputBoundary {
    void prepareSuccessView(UpdateMealsOutputData outputData);
    void prepareFailView(String errorMessage);
}
