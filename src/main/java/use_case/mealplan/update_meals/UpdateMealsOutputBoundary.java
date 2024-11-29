package use_case.mealplan.update_meals;

import use_case.grocery_list.GroceryListOutputData;

public interface UpdateMealsOutputBoundary {
    void prepareSuccessView(UpdateMealsOutputData outputData);
    void prepareFailView(String errorMessage);
}
