package use_case.search;

/**
 * The Output Data for the Search Use Case.
 */
public class SearchOutputData {
    private int recipeId;
    private String recipeName;
    private String imageUrl;

    public SearchOutputData(int recipeId, String recipeName, String imageUrl) {
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
