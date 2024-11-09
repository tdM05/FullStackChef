package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for parsing JSON data in the api calls.
 */
public class JSONParser {
    public static final String FIBER = "Fiber";
    public static final String CARBS = "Carbohydrates";
    public static final String CALORIES = "Calories";
    public static final String PROTEIN = "Protein";
    public static final String FAT = "Fat";
    public static final String AMOUNT = "amount";
    public static final String UNIT = "unit";
    public static final String NA = "NA";
    public static final String STEPS = "steps";
    public static final String NAME = "name";

    /**
     * Parses the ingredients from the JSON response.
     *
     * @param jsonResponse is the JSON response.
     * @return a list of ingredients.
     */
    public static List<Ingredient> parseIngredients(JSONArray jsonResponse) {
        final List<Ingredient> res = new ArrayList<>();
        for (int i = 0; i < jsonResponse.length(); i++) {
            try {
                final JSONObject ingredient = jsonResponse.getJSONObject(i);
                final String name = ingredient.getString(NAME);
                final double amount = ingredient.getDouble("amount");
                final String unit = ingredient.getString("unit");
                final int id = ingredient.getInt("id");
                res.add(new CommonIngredient(name, (float) amount, id, unit));
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    /**
     * Parses the nutritional information from the JSON response.
     *
     * @param jsonResponse is the JSON response.
     * @return the nutritional information.
     */
    public static NutritionalInfo parseNutritionalInfo(JSONObject jsonResponse) {
        final JSONArray nutritionData = jsonResponse.getJSONArray("nutrients");
        final Measurable<Float> calories = new CommonMeasurable<>(0f, NA);
        final Measurable<Float> fat = new CommonMeasurable<>(0f, NA);
        final Measurable<Float> carbs = new CommonMeasurable<>(0f, NA);
        final Measurable<Float> protein = new CommonMeasurable<>(0f, NA);
        final Measurable<Float> fiber = new CommonMeasurable<>(0f, NA);

        for (int i = 0; i < nutritionData.length(); i++) {
            final JSONObject nutrient = nutritionData.getJSONObject(i);
            final String name = nutrient.getString(NAME);

            switch (name) {
                case CALORIES:
                    calories.setNumber(nutrient.getFloat(AMOUNT));
                    calories.setUnit(nutrient.getString(UNIT));
                    break;
                case FAT:
                    fat.setNumber(nutrient.getFloat(AMOUNT));
                    fat.setUnit(nutrient.getString(UNIT));
                    break;
                case CARBS:
                    carbs.setNumber(nutrient.getFloat(AMOUNT));
                    carbs.setUnit(nutrient.getString(UNIT));
                    break;
                case PROTEIN:
                    protein.setNumber(nutrient.getFloat(AMOUNT));
                    protein.setUnit(nutrient.getString(UNIT));
                    break;
                case FIBER:
                    fiber.setNumber(nutrient.getFloat(AMOUNT));
                    fiber.setUnit(nutrient.getString(UNIT));
                    break;
                default:
                    break;
            }
        }

        return new CommonNutritionalInfo(calories, fat, protein, carbs, fiber);
    }

    /**
     * Parses the instructions from the JSON response.
     * The list of ingredients is needed to map the id of the ingredient to the ingredient we already
     * should have in our data.
     *
     * @param jsonResponse is the JSON response.
     * @param storedIngredients is the list of ingredients we already have.
     * @return a list of instructions.
     */
    public static List<Instruction> parseInstructions(JSONArray jsonResponse,
                                                      List<Ingredient> storedIngredients) {
        final JSONObject instructionData = jsonResponse.getJSONObject(0);
        final JSONArray steps = instructionData.getJSONArray(STEPS);
        final List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < steps.length(); i++) {
            final JSONObject step = steps.getJSONObject(i);
            final int number = step.getInt("number");
            // Deal with getting the ingredients.
            // Instructions only return the ingredient ids, so we need to map these
            // ids to actual ingredients from storedIngredients
            final JSONArray ingredientsJson = step.getJSONArray("ingredients");
            final List<Integer> ingredientIds = new ArrayList<>();
            for (int j = 0; j < ingredientsJson.length(); j++) {
                final JSONObject ingredientJson = ingredientsJson.getJSONObject(j);
                ingredientIds.add(ingredientJson.getInt("id"));
            }
            // Finally we get our list of ingredients
            final List<Ingredient> ingredients = getIngredientByIds(ingredientIds, storedIngredients);

            // Now deal with the equipment
            final JSONArray equipmentJson = step.getJSONArray("equipment");
            final List<Equipment> equipments = new ArrayList<>();
            for (int j = 0; j < equipmentJson.length(); j++) {
                final JSONObject equipment = equipmentJson.getJSONObject(j);
                final String image = equipment.getString("image");
                final String name = equipment.getString(NAME);
                final int id = equipment.getInt("id");
                equipments.add(new CommonEquipment(name, id, image));
            }

            // Get the description
            final String description = step.getString("step");
            // Finally we can add a new instruction
            instructions.add(new CommonInstruction(number, ingredients, equipments, description));
        }
        return instructions;
    }

    /**
     * Returns a list of ingredients based on the ids.
     *
     * @param ids is the list of ids.
     * @param storedIngredients is the list of ingredients we already have.
     * @return a list of ingredients based on the ids.
     */
    public static List<Ingredient> getIngredientByIds(
            List<Integer> ids,
            List<Ingredient> storedIngredients
    ) {
        final List<Ingredient> res = new ArrayList<>();
        for (Ingredient ingredient : storedIngredients) {
            if (ids.contains(ingredient.getID())) {
                res.add(ingredient);
            }
        }
        return res;
    }
}
