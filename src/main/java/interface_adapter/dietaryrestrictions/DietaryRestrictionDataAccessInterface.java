package interface_adapter.dietaryrestrictions;

import entity.CommonDietaryRestriction;
import java.io.IOException;

/**
 * The Data Access Interface for Dietary Restrictions.
 */
public interface DietaryRestrictionDataAccessInterface {
    /**
     * Saves the dietary restrictions for a user.
     *
     * @param commonDietaryRestriction the dietary restrictions to save
     * @throws IOException if saving fails
     */
    void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException;

    /**
     * Loads the dietary restrictions for a user.
     *
     * @return the dietary restrictions
     * @throws IOException if loading fails
     */
    CommonDietaryRestriction loadDietaryRestrictions() throws IOException;
}
