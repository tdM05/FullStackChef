package use_case.check_favorite;

/**
 * The Input Data for the Check Favorite Use Case.
 */
public class CheckFavoriteInputData {
    private final int recipeId;

    public CheckFavoriteInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
