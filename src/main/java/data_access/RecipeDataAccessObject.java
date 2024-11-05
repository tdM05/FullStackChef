package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Recipe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeDataAccessObject {
    private static final String API_KEY = "d3bafca8788e40c486fb638c16b08a32";
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final String IMAGE_TYPE = "imageType";
    final List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> getRecipes(String query) {
        final OkHttpClient client = new OkHttpClient();

        String url = String.format("%s?apiKey=%s&query=%s&number=38", BASE_URL, API_KEY, query);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {

                final JSONObject jsonResponse = new JSONObject(response.body().string());
                final JSONArray results = jsonResponse.getJSONArray(RESULTS);

                // Loop through each result and add it to the list
                for (int i = 0; i < results.length(); i++) {
                    final JSONObject recipeJson = results.getJSONObject(i);
                    final int id = recipeJson.getInt(ID);
                    final String title = recipeJson.getString(TITLE);
                    final String image = recipeJson.getString(IMAGE);
                    final String imageType = recipeJson.getString(IMAGE_TYPE);

                    // Create Recipe object and add to the list
                    recipes.add(new Recipe(id, title, image, imageType));
                }
            }
            else {
                System.out.println("API request failed");
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
        return recipes;
    }

    public static void main(String[] args) {
        RecipeDataAccessObject api = new RecipeDataAccessObject();
        List<Recipe> recipes = api.getRecipes("pizza");

        for (Recipe recipe : recipes) {
            System.out.printf("ID: %d, Title: %s, Image URL: %s, Image Type: %s%n",
                    recipe.getId(), recipe.getTitle(), recipe.getImage(), recipe.getImageType());
        }
    }
}
