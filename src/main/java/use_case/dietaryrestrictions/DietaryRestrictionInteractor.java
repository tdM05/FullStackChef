package use_case.dietaryrestrictions;

import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import entity.CommonDietaryRestriction;

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
            // Create CommonDietaryRestriction entity with the selected diets
            CommonDietaryRestriction commonDietaryRestriction = new CommonDietaryRestriction(
                    requestData.getSelectedDiets());
            // Persist the dietary restrictions
            dataAccess.saveDietaryRestrictions(commonDietaryRestriction);

            // After saving, prepare a success response with the updated dietary restrictions
            presenter.prepareSuccessView(new DietaryRestrictionResponseData(
                    "Dietary restrictions saved successfully.", requestData.getSelectedDiets()));
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
    public CommonDietaryRestriction getDietaryRestrictions() throws IOException {
        return dataAccess.loadDietaryRestrictions();
    }
}
