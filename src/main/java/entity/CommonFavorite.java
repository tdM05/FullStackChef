package entity;

import java.util.List;

/**
 * The representation of a favorite recipe in our program.
 */

public class CommonFavorite implements Favorite {

    private final List<Integer> favoriteRecipe;

    public CommonFavorite(List<Integer> favoriteRecipe) {
        this.favoriteRecipe = favoriteRecipe;
    }

    public List<Integer> getFavoriteRecipe() {
        return favoriteRecipe;
    }

    public void addFavoriteRecipe(int recipeId) {
        if (!favoriteRecipe.contains(recipeId)) {
            favoriteRecipe.add(recipeId);
        }
    }


    public void removeFavoriteRecipe(int recipeId) {
        favoriteRecipe.remove(recipeId);
    }


    public boolean isFavorite(int recipeId) {
        return favoriteRecipe.contains(recipeId);
    }


}
