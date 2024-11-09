package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public class CommonRecipe implements Recipe {
    private int id;
    private String title;
    private String image;
    private String imageType;
    private List<Ingredient> ingredients;
    private NutritionalInfo nutritionalInfo;
    private List<Instruction> instructions;
    private boolean isFavorite;

    public CommonRecipe(int id, String title, String image, String imageType,
                        List<Ingredient> ingredients, NutritionalInfo nutritionalInfo,
                        List<Instruction> instructions) {
        this.id = id;
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

    /**
     * THIS METHOD IS NOT NEEDED FOR NOW SINCE WE ARE IMPLEMENTING THE TEAM USE CASE
     * Returns the nutritional information for this recipe.
     *
     * @return the nutritional information for this recipe.
     */
    @Override
    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    // Getters for each field
    public int getId() {
        return id;
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