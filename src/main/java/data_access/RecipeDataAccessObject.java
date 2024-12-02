package data_access;

import entity.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.search.SearchDataAccessInterface;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;

/**
 * The DAO for recipe data.
 * Implements both SearchDataAccessInterface and DisplayRecipeDataAccessInterface.
 */
public class RecipeDataAccessObject implements DisplayRecipeDataAccessInterface, SearchDataAccessInterface {
    private static final String BASE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private static final String BASE_DETAILS_URL = "https://api.spoonacular.com/recipes/%d/information?&apiKey=%s&includeNutrition=true";

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";

    private final List<Recipe> recipes = new ArrayList<>();

    private final OkHttpClient client;

    // Reference the API_KEY from Constants.java
    private static final String API_KEY = Constants.API_KEY;

    /**
     * Constructs a new RecipeDataAccessObject with a default OkHttpClient.
     */
    public RecipeDataAccessObject() {
        this.client = new OkHttpClient();
        System.out.println("Initialized RecipeDataAccessObject with API_KEY: " + API_KEY);
    }

    /**
     * Constructs a new RecipeDataAccessObject with a provided OkHttpClient.
     * Useful for injecting mock clients during testing.
     *
     * @param client the OkHttpClient to use for API requests
     */
    public RecipeDataAccessObject(OkHttpClient client) {
        this.client = client;
        System.out.println("Initialized RecipeDataAccessObject with provided OkHttpClient.");
    }

    /**
     * Retrieves a list of recipes based on the provided ingredients and number of results.
     * This method does not apply any dietary filters.
     *
     * @param ingredients the list of ingredient names
     * @param number      the number of recipes to retrieve
     * @return a list of Recipe entities matching the search criteria
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    @Override
    public List<Recipe> getRecipes(List<String> ingredients, int number) throws IOException, JSONException {
        // Delegate to the method with dietary filters by passing an empty list
        return getRecipes(ingredients, new ArrayList<>(), number);
    }

    /**
     * Retrieves a list of recipes based on the provided ingredients, dietary filters, and number of results.
     *
     * @param ingredientsList the list of ingredient names
     * @param dietaryFilters  the list of dietary restrictions
     * @param number          the number of recipes to retrieve
     * @return a list of Recipe entities matching the search criteria
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    @Override
    public List<Recipe> getRecipes(List<String> ingredientsList, List<String> dietaryFilters, int number) throws IOException, JSONException {
        // Normalize ingredient and dietary filter names to lowercase to match API expectations
        final String ingredientsToString = String.join(",+", ingredientsList).toLowerCase();
        String dietFilters = dietaryFilters.isEmpty() ? "" : "&diet=" + String.join(",", dietaryFilters).toLowerCase();

        final String url = String.format("%s?apiKey=%s&includeIngredients=%s&number=%d%s",
                BASE_SEARCH_URL, API_KEY, ingredientsToString, number, dietFilters);

        // Print the constructed API URL
        System.out.println("Constructed API URL: " + url);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                final JSONObject jsonResponse = new JSONObject(response.body().string());
                final JSONArray results = jsonResponse.getJSONArray(RESULTS);

                // Clear previous search results
                recipes.clear();

                // Loop through each recipe and add it to the list
                for (int i = 0; i < results.length(); i++) {
                    final JSONObject recipeJson = results.getJSONObject(i);
                    final int recipeID = recipeJson.getInt(ID);
                    final String title = recipeJson.getString(TITLE);
                    final String image = recipeJson.getString(IMAGE);
                    final String imageType = recipeJson.optString(IMAGE_TYPE, "jpg"); // Default to "jpg" if not present

                    // Create a Recipe object (details can be fetched later if needed)
                    final Recipe recipe = new CommonRecipe(recipeID, title, image, imageType, null, null, null, false);
                    // Add to the list
                    recipes.add(recipe);
                }
                System.out.println("Fetched " + recipes.size() + " recipes from Spoonacular API.");
                return new ArrayList<>(recipes); // Return a copy to prevent external modification
            } else if (response.body() != null) {
                final JSONObject errorResponse = new JSONObject(response.body().string());
                final String errorMessage = errorResponse.optString("message", "Unknown error occurred.");
                System.err.println("API request failed: " + errorMessage);
                throw new IOException("API request failed: " + errorMessage);
            } else {
                System.err.println("API request failed with status code: " + response.code());
                throw new IOException("API request failed with status code: " + response.code());
            }
        } catch (IOException | JSONException ex) {
            System.err.println("Error during API request: " + ex.getMessage());
            throw new IOException("Error during API request: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves detailed information of a Recipe by its unique identifier.
     *
     * @param id the unique identifier of the recipe
     * @return the Recipe entity corresponding to the given id
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    @Override
    public Recipe getRecipeById(int id) throws IOException, JSONException {
        return fetchAndParseRecipe(id, null, null, null);
    }

    /**
     * Fetches recipe details and parses them into a Recipe entity.
     *
     * @param recipeID   the ID of the recipe
     * @param title      the title of the recipe (optional, used in search)
     * @param image      the image URL of the recipe (optional, used in search)
     * @param imageType  the image type of the recipe (optional, used in search)
     * @return the parsed Recipe entity
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    private Recipe fetchAndParseRecipe(int recipeID, String title, String image, String imageType) throws IOException, JSONException {
        final JSONObject recipeDetails = getRecipeDetails(recipeID);

        // If title, image, and imageType are provided (from search), use them; otherwise, extract from details
        final String finalTitle = (title != null) ? title : recipeDetails.getString("title");
        final String finalImage = (image != null) ? image : recipeDetails.getString("image");
        final String finalImageType = (imageType != null) ? imageType : recipeDetails.optString("imageType", "jpg");

        final JSONArray ingredientsJson = recipeDetails.getJSONArray("extendedIngredients");
        final JSONObject nutritionJson = recipeDetails.getJSONObject("nutrition");
        final JSONArray instructionsJson = recipeDetails.optJSONArray("analyzedInstructions");

        // Parse JSON data to entities
        final List<Ingredient> ingredientObjects = JSONParser.parseIngredients(ingredientsJson);
        final NutritionalInfo nutritionalInfo = JSONParser.parseNutritionalInfo(nutritionJson);
        final List<Instruction> instructions = instructionsJson != null
                ? JSONParser.parseInstructions(instructionsJson, ingredientObjects)
                : new ArrayList<>();

        // Assuming 'isFavorite' is managed locally or via another service
        boolean isFavorite = false; // Placeholder

        return new CommonRecipe(recipeID, finalTitle, finalImage, finalImageType, ingredientObjects,
                nutritionalInfo, instructions, isFavorite);
    }

    /**
     * Retrieves detailed information of a recipe by its ID.
     *
     * @param recipeID the ID of the recipe
     * @return a JSONObject containing detailed recipe information
     * @throws IOException   if an I/O error occurs during the API request
     * @throws JSONException if JSON parsing fails
     */
    private JSONObject getRecipeDetails(int recipeID) throws IOException, JSONException {
        final String url = String.format(BASE_DETAILS_URL, recipeID, API_KEY);
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        System.out.println("Fetching recipe details for ID: " + recipeID);

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject recipeJson = new JSONObject(response.body().string());
                System.out.println("Successfully fetched recipe details for ID: " + recipeID);
                return recipeJson;
            } else if (response.body() != null) {
                final JSONObject errorResponse = new JSONObject(response.body().string());
                final String errorMessage = errorResponse.optString("message", "Unknown error occurred.");
                System.err.println("Failed to fetch recipe details: " + errorMessage);
                throw new IOException("Failed to fetch recipe details: " + errorMessage);
            } else {
                System.err.println("Failed to fetch recipe details with status code: " + response.code());
                throw new IOException("Failed to fetch recipe details with status code: " + response.code());
            }
        } catch (IOException | JSONException ex) {
            System.err.println("Error fetching recipe details: " + ex.getMessage());
            throw new IOException("Error fetching recipe details: " + ex.getMessage(), ex);
        }
    }
}
