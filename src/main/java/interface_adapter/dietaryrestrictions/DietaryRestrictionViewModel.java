package interface_adapter.dietaryrestrictions;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Dietary Restrictions View.
 */
public class DietaryRestrictionViewModel extends ViewModel<DietaryRestrictionState> {

    /**
     * Initializes the DietaryRestrictionViewModel with the default state.
     */
    public DietaryRestrictionViewModel() {
        super("dietaryrestrictions");
        setState(new DietaryRestrictionState());
    }
}
