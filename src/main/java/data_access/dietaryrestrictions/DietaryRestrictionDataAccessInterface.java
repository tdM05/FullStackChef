package data_access.dietaryrestrictions;

import entity.DietaryRestriction;
import java.io.IOException;

/**
 * The Data Access Interface for Dietary Restrictions.
 */
public interface DietaryRestrictionDataAccessInterface {
    /**
     * Saves the dietary restrictions for a user.
     *
     * @param dietaryRestriction the dietary restrictions to save
     * @throws IOException if saving fails
     */
    void saveDietaryRestrictions(DietaryRestriction dietaryRestriction) throws IOException;

    /**
     * Loads the dietary restrictions for a user.
     *
     * @return the dietary restrictions
     * @throws IOException if loading fails
     */
    DietaryRestriction loadDietaryRestrictions() throws IOException;
}
