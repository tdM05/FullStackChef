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
     * Sets the amount of this ingredient.
     * @param amount the amount of this ingredient
     */
    void setAmount(float amount);

    /**
     * Returns the unit of this ingredient.
     * @return the unit of this ingredient
     */
    String getUnit();

    /**
     * Sets the unit of this ingredient.
     * @param unit the unit of this ingredient
     */
    void setUnit(String unit);

    /**
     * Returns the ID of this ingredient.
     * @return the ID of this ingredient
     */
    int getID();
}
