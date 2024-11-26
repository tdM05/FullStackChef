package interface_adapter.dietaryrestrictions;

import use_case.dietaryrestrictions.DietaryRestrictionInputBoundary;
import use_case.dietaryrestrictions.DietaryRestrictionRequestData;

import java.util.List;

/**
 * The Controller for the Dietary Restrictions Use Case.
 * Handles user input and interacts with the interactor.
 */
public class DietaryRestrictionController {
    private final DietaryRestrictionInputBoundary interactor;

    /**
     * Constructs a DietaryRestrictionController with the specified interactor.
     *
     * @param interactor the interactor to handle use case execution
     */
    public DietaryRestrictionController(DietaryRestrictionInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the process to set dietary restrictions.
     *
     * @param selectedDiets the list of selected dietary restrictions
     */
    public void setDietaryRestrictions(List<String> selectedDiets) {
        DietaryRestrictionRequestData requestData = new DietaryRestrictionRequestData(selectedDiets);
        interactor.execute(requestData);
    }
}
