package entity;

import java.time.LocalDate;
import java.util.List;

/**
 * The representation of a meal plan in our program.
 */
public interface MealPlan {
    /**
     * Returns the recipes of the meal plan.
     * @return recipes of the meal plan.
     */
    List<Recipe> getRecipes();

    /**
     * Returns the date associated with this meal plan.
     * @return the date associated with this meal plan.
     */
    LocalDate getDate();

    /**
     * Adds a recipe to the meal plan on a specific date.
     * @param date The date to add the recipe to.
     * @param recipe The recipe to add.
     */
    void addRecipeToDate(LocalDate date, Recipe recipe);

    /**
     * Removes a recipe from the meal plan on a specific date.
     * @param date The date to remove the recipe from.
     * @param recipe The recipe to remove.
     */
    void removeRecipeFromDate(LocalDate date, Recipe recipe);

    /**
     * Gets all recipes for a specific date in the meal plan.
     * @param date The date to get recipes for.
     * @return A list of recipes for the given date.
     */
    List<Recipe> getRecipesForDate(LocalDate date);
}
