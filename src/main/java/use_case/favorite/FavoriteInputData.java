package use_case.favorite;

/**
 * The Input Data for the Favorite Recipe Use Case.
 */
public class FavoriteInputData {
    private final int recipeId;

    public FavoriteInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

}
