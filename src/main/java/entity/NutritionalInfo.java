package entity;

/**
 * The representation of nutritional information for our program.
 */
public interface NutritionalInfo {
    /**
     * Gets the number of calories.
     *
     * @return the number of calories
     */
    Measurable<Float> getCalories();

    /**
     * Gets the amount of fat.
     *
     * @return the amount of fat
     */
    Measurable<Float> getFat();

    /**
     * Gets the amount of protein.
     *
     * @return the amount of protein
     */
    Measurable<Float> getProtein();

    /**
     * Gets the amount of carbohydrates.
     *
     * @return the amount of carbohydrates
     */
    Measurable<Float> getCarbs();

    /**
     * Gets the amount of fiber.
     *
     * @return the amount of fiber
     */
    Measurable<Float> getFiber();
}