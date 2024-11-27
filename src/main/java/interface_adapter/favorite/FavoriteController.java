package interface_adapter.favorite;

import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

/**
 * The controller for managing favorite recipes.
 * It receives user actions and interacts with the use case layer.
 */
public class FavoriteController {
    private final FavoriteInputBoundary favoriteInputBoundary;

    public FavoriteController(FavoriteInputBoundary favoriteInputBoundary){
        this.favoriteInputBoundary = favoriteInputBoundary;
    }

    /**
     * Handles the action of adding or removing a recipe from favorites.
     * @param recipeId the ID of the recipe
     */
    public void executeFavoriteAction(int recipeId){
        final FavoriteInputData favoriteInputData = new FavoriteInputData(recipeId);
        favoriteInputBoundary.executeFavoriteAction(favoriteInputData);
    }

    /**
     * Handles the action of checking if a recipe is a favorite.
     *
     * @param recipeId the ID of the recipe
     */
    public void executeIsFavorite(int recipeId){
        final FavoriteInputData favoriteInputData = new FavoriteInputData(recipeId);
        favoriteInputBoundary.executeIsFavorite(favoriteInputData);
    }

}
