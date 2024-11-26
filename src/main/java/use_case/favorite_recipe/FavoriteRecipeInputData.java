package use_case.favorite_recipe;

/**
 * The Input Data for the Favorite Recipe Use Case.
 */
public class FavoriteRecipeInputData {
    private final int recipeId;

    public FavoriteRecipeInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

}
