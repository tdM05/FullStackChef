package use_case.favorite_recipe;

/**
 * The Input Data for the Favorite Recipe Use Case.
 */
public class FavoriteRecipeInputData {
    private final int recipeId;

    public FavoriteRecipeInputData(int id) {
        this.recipeId = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

}
