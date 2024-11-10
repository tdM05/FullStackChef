package use_case.display_recipe;

/**
 * The Input Data for the Display Recipe Use Case.
 * Contains the necessary information to request a recipe.
 */
public class DisplayRecipeInputData {
    private final int recipeId;

    /**
     * Constructs a new DisplayRecipeInputData with the specified recipe ID.
     *
     * @param recipeId the unique identifier of the recipe
     */
    public DisplayRecipeInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Returns the unique identifier of the recipe.
     *
     * @return the recipe ID
     */
    public int getRecipeId() {
        return recipeId;
    }
}
