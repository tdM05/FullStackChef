package use_case.mealplan.update_meals;

import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.util.List;
import java.util.Map;

/**
 * The Data Access Interface for the Update Meals Use Case.
 */
public interface UpdateMealsDataAccessInterface {
    /**
     * Update the meals in the database.
     * @param username the username of the user
     * @param password the password of the user
     */
    Map<String, List<WeeklyMealRecipeDto>> getMeals(String username, String password);
}
