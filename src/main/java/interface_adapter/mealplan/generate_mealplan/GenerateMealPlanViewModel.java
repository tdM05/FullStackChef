package interface_adapter.mealplan.generate_mealplan;

import interface_adapter.ViewModel;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * ViewModel for the Generate Meal Plan Use Case.
 * Holds the state and data required for rendering the meal plan view.
 */
public class GenerateMealPlanViewModel extends ViewModel<GenerateMealPlanState> {

    private Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan;
    private boolean isLoading;
    private String errorMessage;

    public GenerateMealPlanViewModel() {
        super("generate meal plan");
    }

    /**
     * Updates the meal plan data in the ViewModel.
     *
     * @param mealPlan A map of dates to lists of recipe DTOs.
     */
    public void updateMealPlan(Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan) {
        this.mealPlan = mealPlan;
        this.errorMessage = null;  // Clear any previous error message
        this.isLoading = false;   // Set loading to false after update

        firePropertyChanged("mealPlan");
    }

    /**
     * Updates the ViewModel with an error message.
     *
     * @param errorMessage The error message to set.
     */
    public void updateWithError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.mealPlan = null; // Clear meal plan data on error
        this.isLoading = false; // Set loading to false on error

        firePropertyChanged("errorMessage");
    }

    /**
     * Updates the loading state in the ViewModel.
     *
     * @param isLoading True if loading, false otherwise.
     */
    public void updateLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
        firePropertyChanged("isLoading");
    }

    // Getters for GenerateMealPlanView to retrieve updated values

    /**
     * Retrieves the current meal plan data.
     *
     * @return A map of dates to lists of recipe DTOs, or null if an error occurred.
     */
    public Map<LocalDate, List<GenerateMealPlanRecipeDto>> getMealPlan() {
        return mealPlan;
    }

    /**
     * Checks if the ViewModel is in a loading state.
     *
     * @return True if loading, false otherwise.
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * Retrieves the current error message.
     *
     * @return The error message, or null if no error occurred.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
