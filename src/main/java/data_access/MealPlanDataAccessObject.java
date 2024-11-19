package data_access;

import entity.CommonRecipe;
import entity.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.mealplan.generate_mealplan.GenerateMealPlanDataAccessInterface;

import java.io.IOException;
import java.util.*;

/**
 * A Data Access Object (DAO) implementation for fetching meal plans from the Spoonacular API.
 * This class handles API requests and parses responses into the application's entity model.
 */
public class MealPlanDataAccessObject implements GenerateMealPlanDataAccessInterface {

    private static final String API_KEY = ""; // Replace with your actual API key
    private static final String BASE_URL = "https://api.spoonacular.com/mealplanner/generate"; // Base URL for the Spoonacular API
    private final OkHttpClient client;

    /**
     * Constructs a new MealPlanDataAccessObject with a default OkHttpClient.
     */
    public MealPlanDataAccessObject() {
        this.client = new OkHttpClient();
    }

    /**
     * Generates a weekly meal plan from the Spoonacular API.
     *
     * @param diet      The diet type (e.g., vegetarian, keto). Pass null or an empty string if no specific diet is needed.
     * @param startDate The desired start date for the weekly meal plan (format: YYYY-MM-DD). Currently unused in API requests.
     * @return A map where the keys are day names (e.g., "Monday") and the values are lists of recipes for that day.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String startDate) throws IOException {
        // Construct the API URL
        String url = BASE_URL + "?timeFrame=week&apiKey=" + API_KEY;
        if (diet != null && !diet.isEmpty()) {
            url += "&diet=" + diet;
        }

        // Build and execute the HTTP GET request
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch meal plan");
            }
            // Parse and return the meal plan from the response JSON
            JSONObject jsonResponse = new JSONObject(response.body().string());
            return parseMealPlan(jsonResponse.getJSONObject("week"));
        }
    }

    /**
     * Parses the JSON response from the Spoonacular API to extract the weekly meal plan.
     *
     * @param weekJson The "week" JSON object from the API response.
     * @return A map where the keys are day names (e.g., "Monday") and the values are lists of recipes for that day.
     */
    private Map<String, List<Recipe>> parseMealPlan(JSONObject weekJson) {
        Map<String, List<Recipe>> mealPlan = new LinkedHashMap<>();

        // Map for full day names to their 3-letter abbreviations
        Map<String, String> dayAbbreviations = Map.of(
                "Monday", "Mon",
                "Tuesday", "Tue",
                "Wednesday", "Wed",
                "Thursday", "Thu",
                "Friday", "Fri",
                "Saturday", "Sat",
                "Sunday", "Sun"
        );

        // Iterate over each day of the week in the JSON response
        for (String day : weekJson.keySet()) {
            JSONArray meals = weekJson.getJSONObject(day).getJSONArray("meals");
            List<Recipe> recipes = new ArrayList<>();

            // Extract recipe details for each meal
            for (int i = 0; i < meals.length(); i++) {
                JSONObject meal = meals.getJSONObject(i);
                recipes.add(new CommonRecipe(
                        meal.getInt("id"), // Recipe ID
                        meal.getString("title"), // Recipe title
                        null, // Placeholder for other attributes
                        null,
                        null,
                        null,
                        null,
                        false // Default for isFavorite
                ));
            }

            // Use the abbreviation for the day in the resulting map
            String dayAbbreviation = dayAbbreviations.getOrDefault(day, day); // Fallback to full name if unexpected key
            mealPlan.put(dayAbbreviation, recipes);
        }

        return mealPlan;
    }
}
