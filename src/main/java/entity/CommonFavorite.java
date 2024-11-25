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

    @Override
    public List<Integer> getFavoriteRecipes() {
        return favoriteRecipe;
    }

}
