package interface_adapter.mealplan.generate_mealplan;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import use_case.mealplan.generate_mealplan.WeeklyMealOutputBoundary;
import use_case.mealplan.generate_mealplan.WeeklyMealOutputData;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The presenter for the Weekly Meal use case.
 * Handles the formatting and preparation of meal plan data for display.
 */
public class WeeklyMealPresenter implements WeeklyMealOutputBoundary {
    private final ViewManagerModel viewManager;
    private final WeeklyMealViewModel viewModel;

    /**
     * Constructs a WeeklyMealPresenter with a ViewManagerModel and initializes the ViewModel.
     *
     * @param viewManager The ViewManagerModel for managing view states.
     * @param viewModel   The ViewModel for the Generate Meal Plan use case.
     */
    public WeeklyMealPresenter(ViewManagerModel viewManager, WeeklyMealViewModel viewModel) {
        this.viewManager = viewManager;
        this.viewModel = viewModel;
        System.out.println("WeeklyMealPresenter initialized with ViewManagerModel.");
    }

    /**
     * Prepares the success view by setting the meal plan data in the ViewModel
     * and updating the current view state in the ViewManagerModel.
     *
     * @param outputData The output data containing the meal plan.
     */
    @Override
    public void prepareSuccessView(WeeklyMealOutputData outputData) {
        System.out.println("Preparing success view with meal plan data.");
        Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan = outputData.getMealPlan();
//
//        System.out.println("Generated Weekly Meal Plan:");
//        System.out.println("===========================");

//        for (Map.Entry<LocalDate, List<WeeklyMealRecipeDto>> entry : mealPlan.entrySet()) {
//            String formattedDate = entry.getKey().toString(); // Format the LocalDate as desired
//            System.out.println(formattedDate + ":");
//
//            if (entry.getValue().isEmpty()) {
//                System.out.println("  (No recipes available)");
//            } else {
//                for (WeeklyMealRecipeDto recipe : entry.getValue()) {
//                    System.out.println("  - " + recipe.getTitle() + " (ID: " + recipe.getId() + ")");
//                }
//            }
//        }

        System.out.println("===========================");

        // Update the ViewModel
//        viewModel.updateMealPlan(mealPlan);
//        System.out.println("Meal plan successfully set in ViewModel.");

        // Update the view state in the ViewManagerModel
//        ViewManagerState state = new ViewManagerState(viewModel.getViewName(), null); // Use null for context if no additional context is required
        final WeeklyMealState state = viewModel.getState(); //state is null
        state.setMealPlan(mealPlan);
        viewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view by setting the error message in the ViewModel
     * and updating the current view state in the ViewManagerModel.
     *
     * @param errorMessage The error message to display.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Preparing fail view with error message: " + errorMessage);

        // Update the ViewModel with the error
        viewModel.updateWithError(errorMessage);
        System.out.println("Error message successfully set in ViewModel.");

        // Update the view state in the ViewManagerModel
        viewManager.setState(viewModel.getViewName());
        System.out.println("View state updated in ViewManagerModel.");
    }
    @Override
    public void switchToSearchView() {
        viewManager.setState(Constants.SEARCH_VIEW);
        viewManager.firePropertyChanged();
    }
}

