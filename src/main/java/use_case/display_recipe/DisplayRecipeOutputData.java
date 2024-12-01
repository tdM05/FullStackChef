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
    private final String title;
    private final String image;
    private final String imageType;
    private final List<Ingredient> ingredients;
    private final NutritionalInfo nutritionalInfo;
    private final List<Instruction> instructions;
    private final boolean isFavorite;
    private final String previousViewName;

    /**
     * Constructs a DisplayRecipeOutputData with the specified details.
     *
     * @param recipeId        the unique identifier of the recipe
     * @param title           the title of the recipe
     * @param image           the image URL of the recipe
     * @param imageType       the image type of the recipe (e.g., jpg, png)
     * @param ingredients     the list of ingredients needed for the recipe
     * @param nutritionalInfo the nutritional information of the recipe
     * @param instructions    the instructions to prepare the recipe
     * @param isFavorite      indicates whether the recipe is marked as favorite
     */
    public DisplayRecipeOutputData(int recipeId, String title, String image, String imageType,
                                   List<Ingredient> ingredients, NutritionalInfo nutritionalInfo,
                                   List<Instruction> instructions, boolean isFavorite, String previousViewName) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.ingredients = ingredients;
        this.nutritionalInfo = nutritionalInfo;
        this.instructions = instructions;
        this.isFavorite = isFavorite;
        this.previousViewName = previousViewName;
    }

    // Getters for all fields

    public int getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getPreviousViewName() {
        return previousViewName;
    }


}
