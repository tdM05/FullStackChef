package entity;

import java.util.List;

/**
 * The representation of a single instruction or step in a recipe.
 * A recipe will have multiple instructions.
 */
public class CommonInstruction implements Instruction {
    private int number;
    private List<Ingredient> ingredients;
    private List<Equipment> equipments;
    private String description;

    public CommonInstruction(int number, List<Ingredient> ingredients, List<Equipment> equipments, String description) {
        this.number = number;
        this.ingredients = ingredients;
        this.equipments = equipments;
        this.description = description;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public List<Equipment> getEquipments() {
        return equipments;
    }

    @Override
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
