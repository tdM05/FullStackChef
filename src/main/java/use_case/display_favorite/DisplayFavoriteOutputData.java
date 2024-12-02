package use_case.display_favorite;

/**
 * The Output Data for the Display Favorite Use Case.
 */
public class DisplayFavoriteOutputData {
    private int recipeId;
    private String recipeName;
    private String imageUrl;

    public DisplayFavoriteOutputData(int recipeId, String recipeName, String imageUrl) {
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
