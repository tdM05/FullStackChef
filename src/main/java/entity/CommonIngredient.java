package entity;

import org.jetbrains.annotations.Nullable;

/**
 * The representation of an ingredient in our program.
 */
public class CommonIngredient implements Ingredient {
    private String name;
    private float amount;
    @Nullable
    private Integer id;
    private String unit;

    public CommonIngredient(String name, float amount, int id, String unit) {
        this.name = name;
        this.amount = amount;
        this.id = id;
        this.unit = unit;
    }

    public CommonIngredient(String name, float amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getName() + " - " + getAmount() + " " + getUnit();
    }

}
