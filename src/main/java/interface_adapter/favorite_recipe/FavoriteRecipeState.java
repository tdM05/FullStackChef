package interface_adapter.favorite_recipe;

/**
 * The state of the Favorite Recipe.
 */
public class FavoriteRecipeState {
    private boolean favorite = false;
    private String error;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}