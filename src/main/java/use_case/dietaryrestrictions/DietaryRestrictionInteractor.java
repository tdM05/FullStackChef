package use_case.dietaryrestrictions;

import data_access.DietaryRestrictionDataAccessInterface;
import entity.DietaryRestriction;
import use_case.dietaryrestrictions.DietaryRestrictionInputBoundary;
import use_case.dietaryrestrictions.DietaryRestrictionRequestData;
import use_case.dietaryrestrictions.DietaryRestrictionResponseData;
import use_case.dietaryrestrictions.DietaryRestrictionOutputBoundary;

import java.io.IOException;

/**
 * The Interactor for the Dietary Restrictions Use Case.
 * Implements the DietaryRestrictionInputBoundary to handle requests.
 */
public class DietaryRestrictionInteractor implements DietaryRestrictionInputBoundary {
    private final DietaryRestrictionOutputBoundary presenter;
    private final DietaryRestrictionDataAccessInterface dataAccess;

    /**
     * Constructs a DietaryRestrictionInteractor with the specified presenter and data access.
     *
     * @param presenter  the presenter to output results
     * @param dataAccess the data access interface for dietary restrictions
     */
    public DietaryRestrictionInteractor(DietaryRestrictionOutputBoundary presenter,
                                        DietaryRestrictionDataAccessInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }

    /**
     * Executes the use case to set dietary restrictions.
     *
     * @param requestData the input data containing dietary restrictions
     */
    @Override
    public void execute(DietaryRestrictionRequestData requestData) {
        try {
            // Create DietaryRestriction entity with the selected diets
            DietaryRestriction dietaryRestriction = new DietaryRestriction(requestData.getSelectedDiets());
            // Persist the dietary restrictions
            dataAccess.saveDietaryRestrictions(dietaryRestriction);
            // Prepare success response
            DietaryRestrictionResponseData responseData = new DietaryRestrictionResponseData("Dietary restrictions updated successfully.");
            presenter.prepareSuccessView(responseData);
        } catch (IOException e) {
            presenter.prepareFailView("Failed to update dietary restrictions: " + e.getMessage());
        }
    }

    /**
     * Retrieves the current dietary restrictions.
     *
     * @return the dietary restrictions
     * @throws IOException if loading fails
     */
    public DietaryRestriction getDietaryRestrictions() throws IOException {
        return dataAccess.loadDietaryRestrictions();
    }
}
