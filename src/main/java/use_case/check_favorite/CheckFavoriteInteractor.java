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

    public CheckFavoriteInteractor(CheckFavoriteDataAccessInterface dataAccess, CheckFavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CheckFavoriteInputData checkFavoriteInputData) {
        User user = SessionUser.getInstance().getUser() ;

        if (user == null) {
            presenter.prepareFailView("Error with retrieving user");
            return;
        }

        boolean isFavorite = isFavorite(checkFavoriteInputData.getRecipeId(), user);
        System.out.println("Is favorite: " + isFavorite);

        final CheckFavoriteOutputData outputData = new CheckFavoriteOutputData(isFavorite);
        presenter.prepareSuccessView(outputData);

    }

    boolean isFavorite(int recipeId, User user) {
        List<Integer> favorites = dataAccess.getFavorites(user);
        System.out.println("Favorites: " + favorites);
        return favorites.contains(recipeId);
    }
}
