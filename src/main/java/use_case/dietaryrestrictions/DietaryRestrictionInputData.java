// File: src/main/java/use_case/dietaryrestrictions/DietaryRestrictionInputData.java

package use_case.dietaryrestrictions;

import entity.DietaryRestriction;
import java.util.List;

/**
 * The input data interface for the Dietary Restrictions Use Case.
 */
public interface DietaryRestrictionInputData {
    /**
     * Retrieves the list of dietary restrictions to be saved.
     *
     * @return the list of dietary restrictions
     */
    List<DietaryRestriction> getDietaryRestrictions();
}
