package use_case.mealplan.generate_mealplan;

/**
 * A Data Transfer Object (DTO) for Recipe information.
 */
public class WeeklyMealRecipeDto {
    private final int recipeId;
    private final String title;

    /**
     * Constructs a WeeklyMealRecipeDto object.
     *
     * @param recipeId    The unique ID of the recipe.
     * @param title The title of the recipe.
     */
    public WeeklyMealRecipeDto(int recipeId, String title) {
        this.recipeId = recipeId;
        this.title = title;
    }

    /**
     * Gets the recipe ID.
     *
     * @return The recipe ID.
     */
    public int getRecipeId() {
        return recipeId;
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
