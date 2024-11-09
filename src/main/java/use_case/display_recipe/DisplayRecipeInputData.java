package use_case.display_recipe;

/**
 * Input data for the DisplayRecipe Use Case.
 * Encapsulates the ID of the recipe to be displayed.
 */
public class DisplayRecipeInputData {

    private final int recipeId;

    /**
     * Creates DisplayRecipeInputData with the specified recipe ID.
     *
     * @param recipeId The unique identifier of the recipe to display.
     */
    public DisplayRecipeInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Gets the recipe ID associated with this input data.
     *
     * @return The recipe ID.
     */
    public int getRecipeId() {
        return recipeId;
    }
}
