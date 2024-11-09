package use_case.display_recipe;

import entity.Recipe;
import java.util.List;

/**
 * The Output Data for the Display Recipe Use Case.
 * Contains all necessary information to display a recipe.
 */
public class RecipeOutputData {
    private final String id;
    private final String name;
    private final List<String> ingredients;
    private final List<String> instructions;
    private final List<String> equipment;
    private final NutritionalInfoOutput nutritionalInfo;
    private final boolean isFavorite;

    public RecipeOutputData(String id, String name, List<String> ingredients,
                            List<String> instructions, List<String> equipment,
                            NutritionalInfoOutput nutritionalInfo, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.equipment = equipment;
        this.nutritionalInfo = nutritionalInfo;
        this.isFavorite = isFavorite;
    }

    // Getters
    public String getId() {
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
