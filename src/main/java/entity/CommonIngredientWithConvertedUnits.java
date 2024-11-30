package entity;

/**
 * The representation of an ingredient in our program.
 */
public class CommonIngredientWithConvertedUnits implements IngredientWithConvertedUnits {
    private String name;
    private float sourceAmount;
    private int id;
    private String sourceUnit;
    private String convertedUnit;
    private float convertedAmount;
    private boolean convertStatus;

    /**
     * Constructs a new CommonIngredientWithConvertedUnits with the specified details.
     *
     * @param name the name of the ingredient
     * @param sourceAmount the original amount of the ingredient
     * @param id the ID of the ingredient
     * @param sourceUnit the original unit of the ingredient
     * @param convertedUnit the converted unit of the ingredient
     * @param convertedAmount the converted amount of the ingredient
     * @param convertStatus the status of whether it was converted successfully
     */
    public CommonIngredientWithConvertedUnits(String name, float sourceAmount,
                                              int id, String sourceUnit, String convertedUnit, float convertedAmount,
                                              boolean convertStatus) {
        this.name = name;
        this.sourceAmount = sourceAmount;
        this.id = id;
        this.sourceUnit = sourceUnit;
        this.convertedUnit = convertedUnit;
        this.convertedAmount = convertedAmount;
        this.convertStatus = convertStatus;
    }

    public CommonIngredientWithConvertedUnits(String name, float sourceAmount,
                                              String sourceUnit) {
        this.name = name;
        this.sourceAmount = sourceAmount;
        this.sourceUnit = sourceUnit;
    }

    @Override
    public void setAmount(float amount) {
        sourceAmount = amount;
    }

    @Override
    public void setUnit(String unit) {
        sourceUnit = unit;
    }

    @Override
    public boolean getConvertStatus() {
        return convertStatus;
    }

    @Override
    public void setConvertStatus(boolean newStatus) {
        convertStatus = newStatus;
    }

    /**
     * Returns the name of this ingredient.
     *
     * @return the name of this ingredient
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the original amount of this ingredient.
     *
     * @return the original amount of this ingredient
     */
    @Override
    public float getAmount() {
        return sourceAmount;
    }

    /**
     * Returns the ID of this ingredient.
     *
     * @return the ID of this ingredient
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Returns the original unit of this ingredient.
     *
     * @return the original unit of this ingredient
     */
    @Override
    public String getUnit() {
        return sourceUnit;
    }

    /**
     * Returns the converted unit of this ingredient.
     *
     * @return the converted unit of this ingredient
     */
    @Override
    public String getConvertedUnit() {
        return convertedUnit;
    }

    /**
     * Sets the converted unit of this ingredient.
     *
     * @param convertedUnit the converted unit of this ingredient
     */
    @Override
    public void setConvertedUnit(String convertedUnit) {
        this.convertedUnit = convertedUnit;
    }

    /**
     * Returns the converted amount of this ingredient.
     *
     * @return the converted amount of this ingredient
     */
    @Override
    public float getConvertedAmount() {
        return convertedAmount;
    }

    /**
     * Sets the converted amount of this ingredient.
     *
     * @param convertedAmount the converted amount of this ingredient
     */
    @Override
    public void setConvertedAmount(float convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    /**
     * Returns a string representation of this ingredient.
     *
     * @return a string representation of this ingredient
     */
    @Override
    public String toString() {
        return getName() + " - " + getAmount() + " " + getUnit() + " (converted to " + getConvertedAmount() + " " + getConvertedUnit() + ")";
    }
}
