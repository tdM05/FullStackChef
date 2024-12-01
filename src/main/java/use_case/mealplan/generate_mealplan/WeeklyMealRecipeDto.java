package use_case.mealplan.generate_mealplan;

/**
 * A Data Transfer Object (DTO) for Recipe information.
 */
public class WeeklyMealRecipeDto {
    private final int id;
    private final String title;

    /**
     * Constructs a WeeklyMealRecipeDto object.
     *
     * @param id    The unique ID of the recipe.
     * @param title The title of the recipe.
     */
    public WeeklyMealRecipeDto(int id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Gets the recipe ID.
     *
     * @return The recipe ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the recipe title.
     *
     * @return The recipe title.
     */
    public String getTitle() {
        return title;
    }
}
