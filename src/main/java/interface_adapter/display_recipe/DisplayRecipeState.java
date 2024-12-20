package interface_adapter.display_recipe;

import java.util.List;

/**
 * The state for displaying a recipe.
 * Holds only the essential fields needed for the recipe display view.
 */
public class DisplayRecipeState {
    private String title;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> instructions;
    private boolean isFavorite;
    private String previousViewName;
    private String displayError;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getDisplayError() {
        return displayError;
    }

    public void setDisplayError(String displayError) {
        this.displayError = displayError;
    }

    public String getPreviousViewName() {
        return previousViewName;
    }

    public void setPreviousViewName(String previousViewName) {
        this.previousViewName = previousViewName;
    }

    /**
     * Checks if the state represents an error state.
     * @return true if there is an error message, false otherwise.
     */
    public boolean isError() {
        return displayError != null && !displayError.isEmpty();
    }

    /**
     * Resets the state to clear all fields.
     */
    public void reset() {
        this.title = null;
        this.imageUrl = null;
        this.ingredients = null;
        this.instructions = null;
        this.displayError = null;
        this.isFavorite = false;
        this.previousViewName = null;
    }
}
