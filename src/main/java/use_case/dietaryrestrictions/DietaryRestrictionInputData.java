// File: src/main/java/use_case/dietaryrestrictions/DietaryRestrictionInputData.java

package use_case.dietaryrestrictions;

import java.util.List;

/**
 * The input data interface for the Dietary Restrictions Use Case.
 * This interface should match how the request data is actually used by the interactor.
 */
public interface DietaryRestrictionInputData {
    /**
     * Retrieves the list of dietary restrictions (diets) selected by the user.
     *
     * @return the list of dietary restrictions as strings
     */
    List<String> getSelectedDiets();
}
