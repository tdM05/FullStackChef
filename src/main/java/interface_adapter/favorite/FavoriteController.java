package interface_adapter.favorite;

import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

/**
 * The controller for managing favorite recipes.
 * It receives user actions and interacts with the use case layer.
 */
public class FavoriteController {
    private final FavoriteInputBoundary favoriteInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteInputBoundary) {
        this.favoriteInteractor = favoriteInputBoundary;
    }

    /**
     * Handles the action of adding or removing a recipe from favorites.
     * @param recipeId the ID of the recipe
     */
    public void execute(int recipeId){
        final FavoriteInputData favoriteInputData = new FavoriteInputData(recipeId);
        favoriteInteractor.execute(favoriteInputData);
    }


}
