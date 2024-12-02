package use_case.display_history;

/**
 * The output boundary interface for the Display History Use Case.
 */
public class DisplayHistoryOutputData {
    private int recipeId;
    private String recipeName;
    private String imageUrl;

    public DisplayHistoryOutputData(int recipeId, String recipeName, String imageUrl) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
