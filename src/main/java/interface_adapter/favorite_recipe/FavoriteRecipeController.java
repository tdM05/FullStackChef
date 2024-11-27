package interface_adapter.favorite_recipe;

import use_case.favorite_recipe.FavoriteRecipeInputBoundary;
import use_case.favorite_recipe.FavoriteRecipeInputData;

/**
 * The controller for managing favorite recipes.
 * It receives user actions and interacts with the use case layer.
 */
public class FavoriteRecipeController {
    private final FavoriteRecipeInputBoundary favoriteRecipeInputBoundary;

    public FavoriteRecipeController(FavoriteRecipeInputBoundary favoriteRecipeInputBoundary){
        this.favoriteRecipeInputBoundary = favoriteRecipeInputBoundary;
    }

    /**
     * Handles the action of adding or removing a recipe from favorites.
     * @param recipeId the ID of the recipe
     */
    public void executeFavoriteAction(int recipeId){
        final FavoriteRecipeInputData favoriteRecipeInputData = new FavoriteRecipeInputData(recipeId);
        favoriteRecipeInputBoundary.executeFavoriteAction(favoriteRecipeInputData);
    }

    /**
     * Handles the action of checking if a recipe is a favorite.
     *
     * @param recipeId the ID of the recipe
     */
    public void executeIsFavorite(int recipeId){
        final FavoriteRecipeInputData favoriteRecipeInputData = new FavoriteRecipeInputData(recipeId);
        favoriteRecipeInputBoundary.executeIsFavorite(favoriteRecipeInputData);
    }

}
