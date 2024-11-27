package data_access.grocery_list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data_access.Constants;
import entity.CommonMeasurable;
import entity.CommonPair;
import entity.Measurable;
import entity.Pair;
import entity.CommonIngredientWithConvertedUnits;
import entity.IngredientWithConvertedUnits;
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
    public static final String FAILURE = "failure";

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
        res.add(654959); // add a duplicate to test the simplification
        return res;
    }

    @Override
    public List<IngredientWithConvertedUnits> getAllIngredients(List<Integer> ids) {
        final List<IngredientWithConvertedUnits> res = new ArrayList<>();
        for (int id : ids) {
            final List<IngredientWithConvertedUnits> ingredient = getIngredientByRecipeId(id);
            res.addAll(ingredient);
        }
        return res;
    }

    private List<IngredientWithConvertedUnits> getIngredientByRecipeId(int id) {
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
    private List<IngredientWithConvertedUnits> jsonToIngredients(JSONObject json) {
        // This part maybe goes into json parser maybe not
        // I've realized that it should only go into json parser if it is generic.
        // But this may be only used by this class.
        final List<IngredientWithConvertedUnits> res = new ArrayList<>();
        final JSONArray ingredients = json.getJSONArray("ingredients");
        // loop through each ingredient and add it to the result res
        for (int i = 0; i < ingredients.length(); i++) {
            final JSONObject amount = ingredients.getJSONObject(i)
                            .getJSONObject("amount")
                            .getJSONObject("metric");
            final String name = ingredients.getJSONObject(i).getString("name");
            final String unit = amount.getString("unit");
            final float value = amount.getFloat("value");
            final IngredientWithConvertedUnits tmp = new CommonIngredientWithConvertedUnits(name, value, unit);

            // We need to convert the units to standard units grams.
            final Pair<Measurable<Float>, Boolean> standardUnits = convertToStandardUnits(name, value, unit);

            tmp.setConvertedAmount(standardUnits.getFirst().getNumber());
            tmp.setConvertedUnit(standardUnits.getFirst().getUnit());
            tmp.setConvertStatus(standardUnits.getSecond());
            res.add(tmp);
        }
        return res;
    }

    /**
     * Convert the given value to standard units grams. Returns <converted value, status on whether or not successfully
     * converted>.
     *
     * @param name The name of the ingredient.
     * @param value The value of the ingredient.
     * @param unit The unit of the ingredient.
     * @return The converted value in standard units.
     * @throws GroceryListException If the conversion fails.
     */
    Pair<Measurable<Float>, Boolean> convertToStandardUnits(String name, float value, String unit) throws GroceryListException {
        final String url = String.format(
                "https://api.spoonacular.com/recipes/convert?apiKey=%s&ingredientName=%s&sourceAmount=%f&sourceUnit=%s&targetUnit=grams",
                API_KEY, name, value, unit);
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final Measurable res = new CommonMeasurable<>(responseBody.getFloat("targetAmount"), responseBody.getString("targetUnit"));
            return new CommonPair<>(res, true);
        }
        catch (IOException | JSONException ex) {
            return new CommonPair<>(new CommonMeasurable<>(0f, ""), false);
        }
    }
}
