package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public class CommonRecipe implements Recipe {
    private final int recipeId;
    private final String title;
    private final String image;
    private final String imageType;
    private final List<Ingredient> ingredients;
    private final NutritionalInfo nutritionalInfo;
    private final List<Instruction> instructions;
    private final boolean isFavorite;

    /**
     * Constructs a CommonRecipe with the specified details.
     *
     * @param recipeId         the unique identifier of the recipe
     * @param title            the title of the recipe
     * @param image            the image URL of the recipe
     * @param imageType        the image type of the recipe (e.g., jpg, png)
     * @param ingredients      the list of ingredients needed for the recipe
     * @param nutritionalInfo  the nutritional information of the recipe
     * @param instructions     the instructions to prepare the recipe
     * @param isFavorite       indicates whether the recipe is marked as favorite
     */
    public CommonRecipe(int recipeId, String title, String image, String imageType,
                        List<Ingredient> ingredients, NutritionalInfo nutritionalInfo,
                        List<Instruction> instructions, boolean isFavorite) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.ingredients = ingredients;
        this.nutritionalInfo = nutritionalInfo;
        this.instructions = instructions;
        this.isFavorite = isFavorite;
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getImageType() {
        return imageType;
    }

    @Override
    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public boolean isFavorite() {
        return isFavorite;
    }
}
