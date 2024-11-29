package interface_adapter.mealplan.update_meals;

import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanState;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;
import use_case.mealplan.update_meals.UpdateMealsOutputBoundary;
import use_case.mealplan.update_meals.UpdateMealsOutputData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateMealsPresenter implements UpdateMealsOutputBoundary {
    private UpdateMealsViewModel viewModel;
    public UpdateMealsPresenter(UpdateMealsViewModel viewModel) {
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(UpdateMealsOutputData outputData) {
        Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = outputData.getMealPlan();
        final UpdateMealsState state = viewModel.getState();
        state.setMealPlan(mealPlan);
        viewModel.firePropertyChanged();
    }

    public void prepareFailView(String errorMessage) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
