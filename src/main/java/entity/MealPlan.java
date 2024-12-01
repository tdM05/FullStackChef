package entity;

import java.time.LocalDate;
import java.util.List;

/**:
 * The representation of a meal plan in our program
 */
public interface MealPlan {
    /**
     * Returns the recipes of the meal plan
     * @return recipes of the meal plan
     */
    List<Recipe> getRecipes();

    /**
     * Returns the date associated with this meal plan
     * @return the date associated with this meal plan
     */
    LocalDate getDate();
}

