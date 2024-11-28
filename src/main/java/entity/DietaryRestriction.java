package entity;

import java.util.List;

/**
 * Represents dietary restrictions selected by the user.
 */
public interface DietaryRestriction {
    /**
     * Retrieves the list of diets.
     *
     * @return the list of diets
     */
    List<String> getDiets();
}
