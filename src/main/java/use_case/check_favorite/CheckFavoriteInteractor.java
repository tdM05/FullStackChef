package use_case.check_favorite;

import app.SessionUser;
import entity.User;

import java.util.List;

/**
 * The interactor for checking if a recipe is a favorite.
 */
public class CheckFavoriteInteractor implements CheckFavoriteInputBoundary {
    private final CheckFavoriteDataAccessInterface dataAccess;
    private final CheckFavoriteOutputBoundary presenter;

    private final User user = SessionUser.getInstance().getUser();

    public CheckFavoriteInteractor(CheckFavoriteDataAccessInterface dataAccess, CheckFavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CheckFavoriteInputData checkFavoriteInputData) {
        boolean isFavorite = isFavorite(checkFavoriteInputData.getRecipeId());
        final CheckFavoriteOutputData outputData = new CheckFavoriteOutputData(isFavorite);
        presenter.prepareSuccessView(outputData);

    }

    boolean isFavorite(int recipeId)  {
        List<Integer> favorites = dataAccess.getFavorites(user);
        return favorites.contains(recipeId);
    }
}
