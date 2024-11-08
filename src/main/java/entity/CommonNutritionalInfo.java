package entity;

/**
 * A common implementation of the NutritionalInfo interface.
 */
public class CommonNutritionalInfo implements NutritionalInfo {
    private final Measurable<Float> calories;
    private final Measurable<Float> fat;
    private final Measurable<Float> protein;
    private final Measurable<Float> carbs;
    private final Measurable<Float> fiber;

    /**
     * Constructs a CommonNutritionalInfo object with the specified nutritional values.
     *
     * @param calories the number of calories
     * @param fat the amount of fat
     * @param protein the amount of protein
     * @param carbs the amount of carbohydrates
     * @param fiber the amount of fiber
     */
    public CommonNutritionalInfo(Measurable<Float> calories,
                                 Measurable<Float> fat,
                                 Measurable<Float> protein,
                                 Measurable<Float> carbs,
                                 Measurable<Float> fiber) {
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.fiber = fiber;
    }

    @Override
    public Measurable<Float> getCalories() {
        return calories;
    }


    @Override
    public Measurable<Float> getFat() {
        return fat;
    }

    @Override
    public Measurable<Float> getProtein() {
        return protein;
    }

    @Override
    public Measurable<Float> getCarbs() {
        return carbs;
    }


    @Override
    public Measurable<Float> getFiber() {
        return fiber;
    }
}
