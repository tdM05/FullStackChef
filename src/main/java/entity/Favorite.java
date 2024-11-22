package entity;

import java.util.List;

/**
 * The representation of a favorite recipe in our program.
 */
public interface Favorite {

    /**
     * Get the list of favorite recipe IDs.
     * @return the list of favorite recipe IDs
     */
    List<Integer> getFavoriteRecipe();

    /**
     * Add a recipe to the list of favorite recipes.
     * @param recipeId the ID of the recipe to add
     */
    void addFavoriteRecipe(int recipeId);

    /**
     * Remove a recipe from the list of favorite recipes.
     * @param recipeId the ID of the recipe to remove
     */
    void removeFavoriteRecipe(int recipeId);

    /**
     * Check if a recipe is in the list of favorite recipes.
     * @param recipeId the ID of the recipe to check
     * @return true if the recipe is in the list, false otherwise
     */
    boolean isFavorite(int recipeId);
}
