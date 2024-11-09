package interface_adapter.display_recipe;

/**
 * The state for the DisplayRecipe ViewModel.
 */
public class DisplayRecipeState {
    private String recipeName = "";
    private String ingredients = "";
    private String instructions = "";
    private String displayError;

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getDisplayError() {
        return displayError;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setDisplayError(String displayError) {
        this.displayError = displayError;
    }
}

