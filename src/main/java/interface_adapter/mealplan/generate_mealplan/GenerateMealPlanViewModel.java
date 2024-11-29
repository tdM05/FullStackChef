package interface_adapter.mealplan.generate_mealplan;

import interface_adapter.ViewModel;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;

import java.beans.PropertyChangeSupport;
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
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * Constructor for GenerateMealPlanViewModel.
     * Initializes the ViewModel with the property change support.
     */
    public GenerateMealPlanViewModel() {
        super("generate meal plan");
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Updates the meal plan data in the ViewModel.
     *
     * @param mealPlan A map of dates to lists of recipe DTOs.
     */
    public void updateMealPlan(Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan) {
        if (mealPlan == null) {
            System.err.println("GenerateMealPlanViewModel: Received null mealPlan.");
            Map<LocalDate, List<GenerateMealPlanRecipeDto>> oldMealPlan = this.mealPlan;
            this.mealPlan = null;
            firePropertyChanged("mealPlan", oldMealPlan, this.mealPlan);
            return;
        }

        System.out.println("GenerateMealPlanViewModel: Updating mealPlan with data.");
        Map<LocalDate, List<GenerateMealPlanRecipeDto>> oldMealPlan = this.mealPlan;
        this.mealPlan = mealPlan;
        this.errorMessage = null;  // Clear any error
        this.isLoading = false;   // Set loading to false after update

        firePropertyChanged("mealPlan", oldMealPlan, this.mealPlan); // Notify listeners about the updated meal plan
    }

    /**
     * Updates the ViewModel with an error message.
     *
     * @param errorMessage The error message to set.
     */
    public void updateWithError(String errorMessage) {
        System.err.println("GenerateMealPlanViewModel: Setting error message - " + errorMessage);
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        this.mealPlan = null; // Clear meal plan data on error
        this.isLoading = false; // Set loading to false on error

        firePropertyChanged("errorMessage", oldErrorMessage, this.errorMessage);
    }

    /**
     * Updates the loading state in the ViewModel.
     *
     * @param isLoading True if loading, false otherwise.
     */
    public void updateLoadingState(boolean isLoading) {
        System.out.println("GenerateMealPlanViewModel: Updating loading state to " + isLoading);
        boolean oldIsLoading = this.isLoading;
        this.isLoading = isLoading;
        firePropertyChanged("isLoading", oldIsLoading, this.isLoading);
    }

    /**
     * Fires a property change event to notify listeners of changes to a property.
     *
     * @param propertyName The name of the property that changed.
     * @param oldValue     The old value of the property.
     * @param newValue     The new value of the property.
     */
    private void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Adds a property change listener to the ViewModel.
     *
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from the ViewModel.
     *
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
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
