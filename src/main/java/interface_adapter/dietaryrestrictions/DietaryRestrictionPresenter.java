package interface_adapter.dietaryrestrictions;

import interface_adapter.ViewManagerModel;
import use_case.dietaryrestrictions.DietaryRestrictionOutputBoundary;
import use_case.dietaryrestrictions.DietaryRestrictionResponseData;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * The Presenter for the Dietary Restrictions Use Case.
 * Implements the DietaryRestrictionOutputBoundary to handle presentation logic.
 */
public class DietaryRestrictionPresenter implements DietaryRestrictionOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final Component parentComponent; // Reference to parent UI component for dialogs

    /**
     * Constructs a DietaryRestrictionPresenter with the specified ViewManagerModel and parent component.
     *
     * @param viewManagerModel the view manager to handle view transitions
     * @param parentComponent  the parent component for dialog positioning
     */
    public DietaryRestrictionPresenter(ViewManagerModel viewManagerModel, Component parentComponent) {
        this.viewManagerModel = viewManagerModel;
        this.parentComponent = parentComponent;
    }

    /**
     * Prepares the success view by showing a confirmation dialog.
     *
     * @param responseData the response data containing confirmation
     */
    @Override
    public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
        JOptionPane.showMessageDialog(parentComponent,
                responseData.getMessage(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        // Optionally, transition to another view
        // viewManagerModel.setState("SomeOtherView");
        // viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view by showing an error dialog.
     *
     * @param errorMessage the error message explaining the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
        // Optionally, trigger an error view
        // viewManagerModel.setState("ErrorView");
        // viewManagerModel.firePropertyChanged();
    }
}
