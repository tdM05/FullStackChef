package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public interface Recipe {
    /**
     * Returns the ingredients needed for this recipe.
     *
     * @return a list of ingredients needed for this recipe.
     */
    List<Ingredient> getIngredients();

    /**
     * Returns the nutritional information for this recipe.
     *
     * @return the nutritional information for this recipe.
     */
    NutritionalInfo getNutritionalInfo();

    /**
     * Returns the unique identifier of this recipe.
     *
     * @return the unique identifier of this recipe.
     */
    int getRecipeId();

    /**
     * Returns the title of this recipe.
     *
     * @return the title of this recipe.
     */
    String getTitle();

    /**
     * Returns the image URL of this recipe.
     *
     * @return the image URL of this recipe.
     */
    String getImage();

    /**
     * Returns the image type of this recipe (e.g., jpg, png).
     *
     * @return the image type of this recipe.
     */
    String getImageType();

    /**
     * Returns the instructions for preparing this recipe.
     *
     * @return the instructions for preparing this recipe.
     */
    List<Instruction> getInstructions();

    /**
     * Indicates whether this recipe is marked as a favorite.
     *
     * @return true if the recipe is a favorite, false otherwise.
     */
    boolean isFavorite();
}
