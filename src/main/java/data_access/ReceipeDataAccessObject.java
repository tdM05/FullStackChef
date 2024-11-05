package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Recipe;
import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReceipeDataAccessObject {
    private static final String API_KEY = "d3bafca8788e40c486fb638c16b08a32"; // Replace with your API key
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";

    // JSON keys as constants
    private static final String KEY_RESULTS = "results";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IMAGE_TYPE = "imageType";

    public List<Recipe> getRecipes(String query) {
        List<Recipe> recipes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        // Build the URL with query parameters for 38 results
        String url = String.format("%s?apiKey=%s&query=%s&number=38", BASE_URL, API_KEY, query);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.body().string());
                JSONArray results = jsonResponse.getJSONArray(KEY_RESULTS);

                // Loop through each result and add it to the list
                for (int i = 0; i < results.length(); i++) {
                    JSONObject recipeJson = results.getJSONObject(i);
                    int id = recipeJson.getInt(KEY_ID);
                    String title = recipeJson.getString(KEY_TITLE);
                    String image = recipeJson.getString(KEY_IMAGE);
                    String imageType = recipeJson.getString(KEY_IMAGE_TYPE);

                    // Create Recipe object and add to the list
                    recipes.add(new Recipe(id, title, image, imageType));
                }
            } else {
                System.err.println("API request failed: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public static void main(String[] args) {
        ReceipeDataAccessObject api = new ReceipeDataAccessObject();
        List<Recipe> recipes = api.getRecipes("pizza");

        // Print each recipe
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }
}
