package use_case.dietaryrestrictions;

import app.SessionUser;
import entity.CommonDietaryRestriction;
import entity.User;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;

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
        // Check if user is logged in before attempting to save
        User currentUser = SessionUser.getInstance().getUser();
        if (currentUser == null) {
            // No user logged in, so present a failure view and return
            presenter.prepareFailView("Failed to update dietary restrictions: No user is logged in. Please log in to continue.");
            return;
        }

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
