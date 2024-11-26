package use_case.display_favorite;

public class DisplayFavoriteOutputData {
    int recipeId;
    String recipeName;
    String imageUrl;

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
