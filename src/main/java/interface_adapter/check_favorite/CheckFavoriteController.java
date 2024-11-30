package interface_adapter.check_favorite;

import use_case.check_favorite.CheckFavoriteInputBoundary;
import use_case.check_favorite.CheckFavoriteInputData;

/**
 * The controller for the CheckFavorite Use Case.
 */
public class CheckFavoriteController {
    private final CheckFavoriteInputBoundary checkFavoriteInteractor;

    public CheckFavoriteController(CheckFavoriteInputBoundary checkFavoriteInputBoundary) {
        this.checkFavoriteInteractor = checkFavoriteInputBoundary;
    }

    /**
     * Executes the CheckFavorite Use Case.
     * @param recipeId the unique identifier of the recipe to check
     */
    public void execute(int recipeId) {
        System.out.println(recipeId);
        CheckFavoriteInputData checkFavoriteInputData = new CheckFavoriteInputData(recipeId);
        checkFavoriteInteractor.execute(checkFavoriteInputData);
    }
}
