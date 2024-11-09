package use_case.display_recipe;

import entity.Ingredient;
import entity.Instruction;
import entity.NutritionalInfo;

import java.util.List;

/**
 * The Output Data for the Display Recipe Use Case.
 * Contains all necessary information to display a recipe.
 */
public class DisplayRecipeOutputData {
    private final int recipeId;
    private final String name;
    private final List<Ingredient> ingredients;
    private final List<Instruction> instructions;
    private final List<String> equipment;
    private final NutritionalInfoOutput nutritionalInfo;
    private final boolean isFavorite;

    public DisplayRecipeOutputData(int recipeId, String title, String image, String imageType, List<Ingredient> ingredients, NutritionalInfo nutritionalInfo, List<Instruction> instructions, boolean favorite, String id, String name, List<String> ingredients1, List<String> instructions1, List<String> equipment, NutritionalInfoOutput nutritionalInfo1, boolean isFavorite) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.equipment = equipment;
        this.nutritionalInfo = nutritionalInfo;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getRecipeId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public NutritionalInfoOutput getNutritionalInfo() {
        return nutritionalInfo;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Inner class to represent Nutritional Information.
     */
    public static class NutritionalInfoOutput {
        private final double calories;
        private final double fat;
        private final double carbohydrates;
        private final double protein;

        public NutritionalInfoOutput(double calories, double fat, double carbohydrates, double protein) {
            this.calories = calories;
            this.fat = fat;
            this.carbohydrates = carbohydrates;
            this.protein = protein;
        }

        // Getters
        public double getCalories() {
            return calories;
        }

        public double getFat() {
            return fat;
        }

        public double getCarbohydrates() {
            return carbohydrates;
        }

        public double getProtein() {
            return protein;
        }
    }
}
