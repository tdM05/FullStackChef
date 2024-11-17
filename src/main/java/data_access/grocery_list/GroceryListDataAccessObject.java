package data_access.grocery_list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data_access.Constants;
import entity.CommonIngredient;
import entity.Ingredient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.grocery_list.GroceryListDataAccessInterface;



/**
 * Data access object for the GroceryList.
 */
public class GroceryListDataAccessObject implements GroceryListDataAccessInterface {
    private static final String API_KEY = Constants.API_KEY;
    private static final String STATUS_CODE_LABEL = Constants.STATUS_CODE_LABEL;
    private static final int SUCCESS_CODE = Constants.SUCCESS_CODE;
    private static final String MESSAGE = "message";

    private final OkHttpClient client;

    public GroceryListDataAccessObject() {
        this.client = new OkHttpClient();
    }

    @Override
    public List<Integer> getAllRecipeIds() {
        // TODO get all recipe ids from the profile api.
        final List<Integer> res = new ArrayList<>();
        res.add(716429);
        res.add(654959);
        return res;
    }

    @Override
    public List<Ingredient> getAllIngredients(List<Integer> ids) {
        final List<Ingredient> res = new ArrayList<>();
        for (int id : ids) {
            final List<Ingredient> ingredient = getIngredientByRecipeId(id);
            res.addAll(ingredient);
        }
        return res;
    }

    private List<Ingredient> getIngredientByRecipeId(int id) {
        final JSONObject json = getIngredientJsonById(id);
        return jsonToIngredients(json);
    }

    /**
     * Get the JSON object for an ingredient by its ID.
     *
     * @param id The ID of the ingredient.
     * @return The JSON object.
     * @throws GroceryListException If the request fails.
     */
    private JSONObject getIngredientJsonById(int id) throws GroceryListException {
        final String url = String.format(
                "https://api.spoonacular.com/recipes/%d/ingredientWidget.json?apiKey=%s",
                id, API_KEY);

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody;
        }
        catch (IOException | JSONException ex) {
            throw new GroceryListException("Failed to get ingredient JSON: " + ex.getMessage());
        }
    }

    // This should already be implemented somewhere.
    private List<Ingredient> jsonToIngredients(JSONObject json) {
        // This part maybe goes into json parser maybe not
        // I've realized that it should only go into json parser if it is generic.
        // But this may be only used by this class.
        final List<Ingredient> res = new ArrayList<>();
        final JSONArray ingredients = json.getJSONArray("ingredients");
        for (int i = 0; i < ingredients.length(); i++) {
            final JSONObject amount = ingredients.getJSONObject(i)
                            .getJSONObject("amount")
                            .getJSONObject("metric");
            final String name = ingredients.getJSONObject(i).getString("name");
            final String unit = amount.getString("unit");
            final int value = amount.getInt("value");
            final Ingredient tmp = new CommonIngredient(name, value, unit);
            res.add(tmp);
        }
        return res;
    }
}
