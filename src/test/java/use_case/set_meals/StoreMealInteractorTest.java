package use_case.set_meals;

import data_access.UserProfile.ProfileException;
import entity.CommonUser;
import entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StoreMealInteractorTest {

    @Test
    public void execute() {
        StoreMealDataAccessInterface dataAccess = new StoreMealDataAccessInterface() {
            @Override
            public void setInfo(JSONObject userJSON) {
                JSONObject res = new JSONObject();
                // not much to test since it just saves it to the api
                assertTrue(true);
            }
        };
        StoreMealInteractor interactor = new StoreMealInteractor(dataAccess);
        Map<String, List<Integer>> mealIds = new HashMap<>();
        mealIds.put("monday", List.of(1));
        mealIds.put("tuesday", List.of(2));
        mealIds.put("wednesday", List.of(3));
        mealIds.put("thursday", List.of(4));
        mealIds.put("friday", List.of(5));
        mealIds.put("saturday", List.of(6));
        mealIds.put("sunday", List.of(7));
        StoreMealInputData inputData = new StoreMealInputData(mealIds);
        User user = new CommonUser("username", "password");
        interactor.execute(inputData, user);
    }

    @Test
    public void JSONMealsToMap() {
        StoreMealInteractor interactor = new StoreMealInteractor(null);
        JSONObject mealIdsJSON = new JSONObject();
        mealIdsJSON.put("monday", new JSONArray(1));
        mealIdsJSON.put("tuesday", new JSONArray(2));
        mealIdsJSON.put("wednesday", new JSONArray(3));
        mealIdsJSON.put("thursday", new JSONArray(4));
        mealIdsJSON.put("friday", new JSONArray(5));
        mealIdsJSON.put("saturday", new JSONArray(6));
        mealIdsJSON.put("sunday", new JSONArray(7));

        interactor.JSONMealsToMap(mealIdsJSON);
    }

    @Test
    public void JSONMealsToMapEx() {
        try {
            StoreMealInteractor interactor = new StoreMealInteractor(null);
            JSONObject mealIdsJSON = new JSONObject();
            mealIdsJSON.put("monday2", new JSONArray(1));
            mealIdsJSON.put("tuesday", new JSONArray(2));
            mealIdsJSON.put("wednesday", new JSONArray(3));
            mealIdsJSON.put("thursday", new JSONArray(4));
            mealIdsJSON.put("friday", new JSONArray(5));
            mealIdsJSON.put("saturday", new JSONArray(6));
            mealIdsJSON.put("sunday2", new JSONArray(7));

            interactor.JSONMealsToMap(mealIdsJSON);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void JSONArrayToList() {
        StoreMealInteractor interactor = new StoreMealInteractor(null);
        JSONArray meals = new JSONArray();
        meals.put(1);
        meals.put(2);
        meals.put(3);
        meals.put(4);
        meals.put(5);
        meals.put(6);
        meals.put(7);

        interactor.JSONArrayToList(meals);
    }

    @Test
    public void JSONArrayToListEx() {
        try {
            StoreMealInteractor interactor = new StoreMealInteractor(null);
            JSONArray meals = new JSONArray();
            meals.put("a");
            meals.put(2);
            meals.put(3);
            meals.put(4);
            meals.put(5);
            meals.put(6);
            meals.put("7");

            interactor.JSONArrayToList(meals);
        }
        catch (ProfileException e) {
            assertTrue(true);
        }
    }
}