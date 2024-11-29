package interface_adapter.mealplan.generate_mealplan;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import use_case.mealplan.generate_mealplan.GenerateMealPlanOutputBoundary;
import use_case.mealplan.generate_mealplan.GenerateMealPlanOutputData;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The presenter for the Generate Meal Plan use case.
 * Handles the formatting and preparation of meal plan data for display.
 */
public class GenerateMealPlanPresenter implements GenerateMealPlanOutputBoundary {
    private final ViewManagerModel viewManager;
    private final GenerateMealPlanViewModel viewModel;

    /**
     * Constructs a GenerateMealPlanPresenter with a ViewManagerModel and initializes the ViewModel.
     *
     * @param viewManager The ViewManagerModel for managing view states.
     * @param viewModel   The ViewModel for the Generate Meal Plan use case.
     */
    public GenerateMealPlanPresenter(ViewManagerModel viewManager, GenerateMealPlanViewModel viewModel) {
        this.viewManager = viewManager;
        this.viewModel = viewModel;
        System.out.println("GenerateMealPlanPresenter initialized with ViewManagerModel.");
    }

    /**
     * Prepares the success view by setting the meal plan data in the ViewModel
     * and updating the current view state in the ViewManagerModel.
     *
     * @param outputData The output data containing the meal plan.
     */
    @Override
    public void prepareSuccessView(GenerateMealPlanOutputData outputData) {
        System.out.println("Preparing success view with meal plan data...");
        Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = outputData.getMealPlan();

        System.out.println("Generated Weekly Meal Plan:");
        System.out.println("===========================");

        for (Map.Entry<LocalDate, List<GenerateMealPlanRecipeDto>> entry : mealPlan.entrySet()) {
            String formattedDate = entry.getKey().toString(); // Format the LocalDate as desired
            System.out.println(formattedDate + ":");

            if (entry.getValue().isEmpty()) {
                System.out.println("  (No recipes available)");
            } else {
                for (GenerateMealPlanRecipeDto recipe : entry.getValue()) {
                    System.out.println("  - " + recipe.getTitle() + " (ID: " + recipe.getId() + ")");
                }
            }
        }

        System.out.println("===========================");

        // Update the ViewModel
        viewModel.updateMealPlan(outputData.getMealPlan());
        System.out.println("Meal plan successfully set in ViewModel.");

        // Update the view state in the ViewManagerModel
        ViewManagerState state = new ViewManagerState(viewModel.getViewName(), null); // Use null for context if no additional context is required
        viewManager.setState(state);
        System.out.println("View state updated in ViewManagerModel.");
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
        ViewManagerState state = new ViewManagerState(viewModel.getViewName(), null); // Use null for context if no additional context is required
        viewManager.setState(state);
        System.out.println("View state updated in ViewManagerModel.");
    }
}
