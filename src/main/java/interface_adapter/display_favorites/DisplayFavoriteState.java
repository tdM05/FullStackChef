package interface_adapter.display_favorites;

import use_case.display_favorite.DisplayFavoriteOutputData;

import java.util.List;

/**
 * The State for the Display Favorites View.
 */
public class DisplayFavoriteState {
    private List<DisplayFavoriteOutputData> favoriteRecipes;
    private String error;

    public List<DisplayFavoriteOutputData> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<DisplayFavoriteOutputData> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
