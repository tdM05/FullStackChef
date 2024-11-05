package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public interface Recipe {
    /**
     * Returns the ingredients needed for this recipe.
     * @return the ingredients needed for this recipe.
     */
    List<Ingredient> getIngredients();
}
