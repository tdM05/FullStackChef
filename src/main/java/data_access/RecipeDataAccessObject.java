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
import use_case.search_recipe.SearchRecipeDataAccessInterface;

/**
 * The DAO for recipe data.
 */
public class RecipeDataAccessObject implements SearchRecipeDataAccessInterface {
    private static final String API_KEY = "6c12c2fcd75b40569836eb71339e80be";
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";
    private final List<Recipe> recipes = new ArrayList<>();

    @Override
    public List<Recipe> getRecipes(List<String> ingredientsString, int number) throws IOException, JSONException {
        final OkHttpClient client = new OkHttpClient();
        final String ingredientsToString = String.join(",+", ingredientsString);
        final String url = String.format("%s?apiKey=%s&includeIngredients=%s&number=%s",
                BASE_URL, API_KEY, ingredientsToString, number);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {

                final JSONObject jsonResponse = new JSONObject(response.body().string());
                // This gets the stuff from jsonResponse with keY "results"
                final JSONArray results = jsonResponse.getJSONArray(RESULTS);

                // Loop through each recipe and add it to the list
                for (int i = 0; i < results.length(); i++) {
                    // this is a recipe
                    final JSONObject recipeJson = results.getJSONObject(i);
                    final int recipeID = recipeJson.getInt(ID);
                    final String title = recipeJson.getString(TITLE);
                    final String image = recipeJson.getString(IMAGE);
                    final String imageType = recipeJson.getString(IMAGE_TYPE);

                    // We need to get all details from the recipe as JSON first

                    final JSONObject recipeDetails = getRecipeDetails(recipeID);

                    final JSONArray ingredientsJson = recipeDetails.getJSONArray("extendedIngredients");
                    final JSONObject nutritionJson = recipeDetails.getJSONObject("nutrition");
                    final JSONArray instructionsJson = recipeDetails.getJSONArray("analyzedInstructions");

                    // Then we convert these details to entities using a json parser
                    final List<Ingredient> ingredientObjects = JSONParser.parseIngredients(ingredientsJson);
                    final NutritionalInfo nutritionalInfo = JSONParser.parseNutritionalInfo(nutritionJson);
                    final List<Instruction> instructions = JSONParser.parseInstructions(instructionsJson,
                            ingredientObjects);

                    // Create Recipe object and add to the list
                    recipes.add(new CommonRecipe(recipeID, title, image, imageType, ingredientObjects,
                            nutritionalInfo, instructions));
                }
                return recipes;
            }
            else if (response.body() != null) {
                final JSONObject errorResponse = new JSONObject(response.body().string());
                final String errorMessage = errorResponse.getString("message");
                throw new IOException("API request failed: " + errorMessage);
            }
            else {
                throw new IOException("API request failed with status code: " + response.code());
            }
        }
        catch (IOException ex) {
            throw new IOException(ex);
        }
        catch (JSONException ex) {
            throw new JSONException(ex);
        }
    }

    /**
     * Returns instructions, ingredients, and nutrition of a recipe based on the id.
     *
     * @param recipeID is the id of the recipe.
     * @return the details of a recipe based on the id.
     * @throws RuntimeException if an I/O or JSON parsing error occurs.
     */
    private JSONObject getRecipeDetails(int recipeID) {
        final OkHttpClient client = new OkHttpClient();
        final String url = String.format(
                "https://api.spoonacular.com/recipes/%d/information?&apiKey=%s&includeNutrition=true",
                recipeID, API_KEY);
        JSONObject result = null;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                result = new JSONObject(response.body().string());
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

}
