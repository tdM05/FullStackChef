package entity;

import java.util.List;

/**
 * Represents dietary restrictions selected by the user.
 */
public class DietaryRestriction {
    private final List<String> diets;

    /**
     * Constructs a DietaryRestriction with the specified list of diets.
     *
     * @param diets the list of diets (e.g., Vegetarian, Vegan)
     */
    public DietaryRestriction(List<String> diets) {
        this.diets = diets;
    }

    /**
     * Retrieves the list of diets.
     *
     * @return the list of diets
     */
    public List<String> getDiets() {
        return diets;
    }
}
