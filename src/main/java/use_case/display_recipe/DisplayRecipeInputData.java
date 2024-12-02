package use_case.display_recipe;

/**
 * The Input Data for the Display Recipe Use Case.
 * Contains the necessary information to request a recipe.
 */
public class DisplayRecipeInputData {
    private final int recipeId;
    private final String previousViewName;

    /**
     * Constructs a new DisplayRecipeInputData with the specified recipe ID.
     *
     * @param recipeId the unique identifier of the recipe
     */
    public DisplayRecipeInputData(int recipeId, String previousViewName) {
        this.recipeId = recipeId;
        this.previousViewName = previousViewName;
    }

    /**
     * Returns the unique identifier of the recipe.
     *
     * @return the recipe ID
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * Returns the name of the previous view.
     *
     * @return the previous view name
     */
    public String getPreviousViewName() {
        return previousViewName;
    }
}
