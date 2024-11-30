package use_case.favorite_recipe;

/**
 * The input boundary for the Favorite Recipe Use Case.
 */
public interface FavoriteRecipeInputBoundary {

    /**
     * Executes the Favorite Recipe Use Case. Dynamically determines whether to add
     * or remove a recipe based on its current state in the user's favorites.
     * @param favoriteRecipeInputData the input data for the use case
     */
    void executeFavoriteAction(FavoriteRecipeInputData favoriteRecipeInputData);

    /**
     * Executes the Favorite Recipe Use Case. Determines whether a recipe is in the user's favorites.
     * @param favoriteRecipeInputData the input data for the use case
     */
    void executeIsFavorite(FavoriteRecipeInputData favoriteRecipeInputData);
}
