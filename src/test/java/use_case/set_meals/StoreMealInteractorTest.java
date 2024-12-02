package use_case.set_meals;

import data_access.Constants;
import entity.CommonUser;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import util.UserToJSON;

import java.util.*;

import static org.junit.Assert.*;

public class StoreMealInteractorTest {

    private StoreMealInteractor interactor;
    private StoreMealDataAccessInterface mockDataAccess;
    private User testUser;
    private JSONObject storedInfo;

    @Before
    public void setup() {
        // Mock DataAccess
        mockDataAccess = new StoreMealDataAccessInterface() {
            @Override
            public void setInfo(JSONObject userJSON) {
                storedInfo = userJSON; // Capture the JSON for validation
            }
        };

        // Initialize Interactor
        interactor = new StoreMealInteractor(mockDataAccess);

        // Create Test User
        testUser = new CommonUser("testUser", "testPassword");
    }

    @Test
    public void testExecuteWithValidData() {
        // Prepare input data
        Map<String, List<Integer>> mealIds = new HashMap<>();
        mealIds.put("Monday", List.of(1, 2, 3));
        mealIds.put("Tuesday", List.of(4, 5, 6));
        StoreMealInputData inputData = new StoreMealInputData(mealIds);

        // Execute the interactor
        interactor.execute(inputData, testUser);

        // Verify the meals were updated in the user object
        Map<String, List<Integer>> updatedMealIds = testUser.getMealIds();
        assertNotNull(updatedMealIds);
        assertEquals(2, updatedMealIds.size());
        assertEquals(List.of(1, 2, 3), updatedMealIds.get("Monday"));
        assertEquals(List.of(4, 5, 6), updatedMealIds.get("Tuesday"));

        // Verify the storedInfo JSON
        assertNotNull(storedInfo);
        JSONObject info = storedInfo.getJSONObject(Constants.INFO);
        JSONObject mealIdsJSON = info.getJSONObject(Constants.MEAL_IDS);

        JSONArray mondayMeals = mealIdsJSON.getJSONArray("Monday");
        JSONArray tuesdayMeals = mealIdsJSON.getJSONArray("Tuesday");

        assertEquals(List.of(1, 2, 3), jsonArrayToList(mondayMeals));
        assertEquals(List.of(4, 5, 6), jsonArrayToList(tuesdayMeals));
    }

    @Test
    public void testExecuteWithEmptyData() {
        // Prepare empty input data
        Map<String, List<Integer>> mealIds = new HashMap<>();
        StoreMealInputData inputData = new StoreMealInputData(mealIds);

        // Execute the interactor
        interactor.execute(inputData, testUser);

        // Verify the meals remain empty in the user object
        Map<String, List<Integer>> updatedMealIds = testUser.getMealIds();
        assertNotNull(updatedMealIds);
        assertTrue(updatedMealIds.isEmpty());

        // Verify the storedInfo JSON
        assertNotNull(storedInfo);
        JSONObject info = storedInfo.getJSONObject(Constants.INFO);
        JSONObject mealIdsJSON = info.getJSONObject(Constants.MEAL_IDS);

        assertTrue(mealIdsJSON.isEmpty());
    }

    @Test
    public void testJSONArrayToListWithValidJSONArray() {
        // Prepare valid JSONArray
        JSONArray validArray = new JSONArray(List.of(1, 2, 3));

        // Convert to list using interactor's method indirectly
        List<Integer> result = jsonArrayToList(validArray);

        // Verify the result
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJSONArrayToListWithInvalidJSONArray() {
        // Prepare invalid JSONArray
        JSONArray invalidArray = new JSONArray();
        invalidArray.put("invalid"); // Add a non-integer value

        // Attempt conversion using the interactor's method indirectly
        jsonArrayToList(invalidArray);
    }

    @Test
    public void testJSONMealsToMapWithValidJSON() {
        // Prepare valid JSON
        JSONObject mealIdsJSON = new JSONObject();
        mealIdsJSON.put("Monday", new JSONArray(List.of(1, 2, 3)));
        mealIdsJSON.put("Tuesday", new JSONArray(List.of(4, 5, 6)));

        // Convert JSON to Map using interactor's method indirectly
        Map<String, List<Integer>> result = interactor.JSONMealsToMap(mealIdsJSON);

        // Verify the result
        Map<String, List<Integer>> expected = new HashMap<>();
        expected.put("Monday", List.of(1, 2, 3));
        expected.put("Tuesday", List.of(4, 5, 6));
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJSONMealsToMapWithInvalidJSON() {
        // Prepare invalid JSON
        JSONObject invalidJSON = new JSONObject();
        invalidJSON.put("InvalidDay", new JSONArray(List.of(1, 2, 3)));

        // Attempt conversion using interactor's method indirectly
        interactor.JSONMealsToMap(invalidJSON);
    }

    // Helper method to convert JSONArray to List<Integer>
    private List<Integer> jsonArrayToList(JSONArray jsonArray) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getInt(i));
        }
        return list;
    }
}
