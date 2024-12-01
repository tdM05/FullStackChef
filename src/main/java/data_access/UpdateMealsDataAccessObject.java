package data_access;

import data_access.UserProfile.ProfileException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;
import use_case.mealplan.update_meals.UpdateMealsDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateMealsDataAccessObject implements UpdateMealsDataAccessInterface {
    private OkHttpClient client;
    public UpdateMealsDataAccessObject() {
        client = new OkHttpClient();
    }
    @Override
    public Map<String, List<WeeklyMealRecipeDto>> getMeals(String username, String password) {
        // we need to get the ids from profile api
        // we get a Map<String, List<Integer>> from the profile api
        final Map<String, List<Integer>> mealIds = StringIntegerMap(username, password);

        // then we need to convert these ids to meals using spoonacular api
        // We do this by going through each key one by one, and calling the spoonacular api in bulk for all ids
        // So we only need 7 api calls
        // in the end we parse the json and return a Map<String, List<GenerateMealPlanRecipeDto>>
        Map<String, List<WeeklyMealRecipeDto>> res = new HashMap<>();
        for (String key : mealIds.keySet()) {
            final List<Integer> recipeIds = mealIds.get(key);
            final List<WeeklyMealRecipeDto> recipeDto = recipeIdsToDto(recipeIds);
            res.put(key, recipeDto);
        }
        return res;
    }

    /**
     * This calls profile api to return a map with key as dates and values as a list of meal ids (ints)
     * @param username the username
     * @param password the password
     * @return a map with key as dates and values as a list of meal ids (ints)
     * @throws ProfileException if the profile api fails
     */
    private Map<String, List<Integer>> StringIntegerMap(String username, String password) throws ProfileException {
        //http://vm003.teach.cs.toronto.edu:20112/user?username=test_user
        final String url = "http://vm003.teach.cs.toronto.edu:20112/user?username=" + username;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final int code = responseBody.getInt("status_code");
            final String message = responseBody.getString(Constants.MESSAGE);
            if (code != Constants.SUCCESS_CODE || !message.equals("User retrieved successfully")) {
                throw new ProfileException(message);
            }
            Map<String, List<Integer>> res = new HashMap<>();
            final JSONObject userJson = responseBody.getJSONObject("user");
            final JSONObject info = userJson.getJSONObject("info");
            final JSONObject meals = info.getJSONObject(Constants.MEAL_IDS);
            for (String key : meals.keySet()) {
                final List<Integer> mealIds = new ArrayList<>();
                final JSONArray mealIdsJson = meals.getJSONArray(key);
                for (int i = 0; i < mealIdsJson.length(); i++) {
                    mealIds.add(mealIdsJson.getInt(i));
                }
                res.put(key, mealIds);
            }
            return res;
        }
        catch (Exception ex) {
            throw new ProfileException("Failed to fetch user profile: " + ex.getMessage());
        }
    }

    /**
     * This converts List<Integer> to List<WeeklyMealRecipeDto>.
     * So it needs to call spoonacular api to get the recipe name too.
     * @param recipeIds a list of recipe ids
     * @return a list of GenerateMealPlanRecipeDto
     * @throws ProfileException
     */
    private List<WeeklyMealRecipeDto> recipeIdsToDto(List<Integer> recipeIds) throws ProfileException{
        //https://api.spoonacular.com/recipes/informationBulk?ids=715538,716429
        // use the batch api endpoint
        String ids = "";
        for (int i = 0; i < recipeIds.size(); i++) {
            ids += recipeIds.get(i);
            if (i != recipeIds.size() - 1) {
                ids += ",";
            }
        }

        JSONObject recipeObjects = new JSONObject();

        // make the api calls to fill recipeObjects
        String url = "https://api.spoonacular.com/recipes/informationBulk?ids=" + ids+"&apiKey="+Constants.API_KEY;
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONArray responseBody = new JSONArray(response.body().string());
            final List<WeeklyMealRecipeDto> res = new ArrayList<>();
            // loop through each recipe object and add to res
            for (int i = 0; i < responseBody.length(); i++) {
                final JSONObject recipe = responseBody.getJSONObject(i);
                final String title = recipe.getString("title");
                final int id = recipe.getInt("id");
                res.add(new WeeklyMealRecipeDto(id, title));
            }
            return res;
        }
        catch (Exception ex) {
            throw new ProfileException("Failed to fetch user profile: " + ex.getMessage());
        }
    }
}
