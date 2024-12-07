package use_case.dietaryrestrictions;

import java.util.List;

/**
 * The Request Data for the Dietary Restrictions Use Case.
 * Contains the dietary restrictions selected by the user.
 */
public class DietaryRestrictionRequestData implements DietaryRestrictionInputData {
    private final List<String> selectedDiets;

    /**
     * Constructs DietaryRestrictionRequestData with the specified diets.
     *
     * @param selectedDiets the list of selected dietary restrictions
     */
    public DietaryRestrictionRequestData(List<String> selectedDiets) {
        this.selectedDiets = selectedDiets;
    }

    /**
     * Retrieves the selected dietary restrictions.
     *
     * @return the list of selected diets
     */
    public List<String> getSelectedDiets() {
        return selectedDiets;
    }
}
