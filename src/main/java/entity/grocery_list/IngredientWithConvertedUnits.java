package entity.grocery_list;

import entity.Ingredient;

/**
 * This class is necessary for ingredients that have been converted to some standard.
 */
public interface IngredientWithConvertedUnits extends Ingredient {
    /**
     * Returns the converted/target unit of this ingredient.
     * @return the unit of this ingredient
     */
    String getConvertedUnit();

    /**
     * Sets the converted/target unit of this ingredient.
     * @param unit the unit of this ingredient
     */
    void setConvertedUnit(String unit);

    /**
     * Returns the converted/target amount, in grams of this ingredient.
     * @return the amount, in grams of this ingredient
     */
    float getConvertedAmount();

    /**
     * Sets the converted/target amount, in grams of this ingredient.
     * @param amount the amount, in grams of this ingredient
     */
    void setConvertedAmount(float amount);

    /**
     * Returns true if the ingredient is successfully converted, false otherwise.
     * @return the status of this ingredient
     */
    boolean getConvertStatus();

    /**
     * Sets the status of whether or not it was converted successfully.
     * @param newStatus the status of this ingredient
     */
    void setConvertStatus(boolean newStatus);
}
