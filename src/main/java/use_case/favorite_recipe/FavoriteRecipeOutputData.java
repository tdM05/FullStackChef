package use_case.favorite_recipe;

/**
 * The Output Data for the Favorite Recipe Use Case.
 */
public class FavoriteRecipeOutputData {
    private final boolean favorite;

    public FavoriteRecipeOutputData(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean getFavorite() {
        return favorite;
    }


}
