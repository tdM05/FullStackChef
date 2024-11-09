package entity;

/**
 * The representation of an ingredient in our program.
 */
public class CommonIngredient implements Ingredient {
    private String name;
    private float amount;
    private int id;
    private String unit;

    public CommonIngredient(String name, float amount, int id, String unit) {
        this.name = name;
        this.amount = amount;
        this.id = id;
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
}
