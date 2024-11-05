package entity;

/**
 * The representation of an ingredient in our program.
 */
public interface Ingredient {
    /**
     * Returns the name of this ingredient.
     * @return the name of this ingredient
     */
    String getName();

    /**
     * Returns the amount, in grams of this ingredient.
     * We may have some other function that converts units that will not be in this interface.
     * @return the amount, in grams of this ingredient
     */
    float getAmount();

    /**
     * Returns the nutritional information of this ingredient.
     * @return the nutritional information of this ingredient
     */
    NutritionalInfo getNutritionalInfo();

}
