package use_case.mealplan.generate_mealplan;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The Data Access Interface for the Weekly Meal Use Case.
 * Defines the methods required for generating and managing meal plans.
 */
public interface WeeklyMealDataAccessInterface {

    /**
     * Generates a weekly meal plan from the Spoonacular API.
     *
     * @param diet      (Optional) The diet type (e.g., vegetarian, keto, etc.).
     * @param startDate The desired start date for the weekly meal plan (format: YYYY-MM-DD).
     * @return A map of days to a list of Recipe objects.
     * @throws IOException   If an I/O error occurs during the API request.
     * @throws JSONException If JSON parsing fails.
     */
    Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String startDate)
            throws IOException, JSONException;
}
