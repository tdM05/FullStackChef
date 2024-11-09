package entity;

import java.util.List;

/**
 * The representation of a single instruction or step in a recipe.
 * A recipe will have multiple instructions
 */
public interface Instruction {
    /**
     * Gets the instruction number. For example number 1 means that this is the first instruction in the recipe.
     *
     * @return the number
     */
    int getNumber();

    /**
     * Sets the instruction number.
     *
     * @param number the number
     */
    void setNumber(int number);

    /**
     * Gets the ingredients used in this instruction.
     *
     * @return the ingredients
     */
    List<Ingredient> getIngredients();

    /**
     * Sets the ingredients used in this instruction.
     *
     * @param ingredients the ingredients
     */
    void setIngredients(List<Ingredient> ingredients);

    /**
     * Gets the equipments used in this instruction.
     *
     * @return the equipments
     */
    List<Equipment> getEquipments();

    /**
     * Sets the equipments used in this instruction.
     *
     * @param equipments the equipments
     */
    void setEquipments(List<Equipment> equipments);

    /**
     * Gets the description of the instruction.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Sets the description of the instruction.
     *
     * @param description the description
     */
    void setDescription(String description);
}
