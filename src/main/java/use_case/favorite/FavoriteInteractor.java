package use_case.favorite;

import app.SessionUser;
import entity.User;

import java.util.List;

/**
 * The interactor for the Favorite Recipe Use Case.
 */
public class FavoriteInteractor implements FavoriteInputBoundary {
    private final FavoriteDataAccessInterface dataAccess;
    private final FavoriteOutputBoundary presenter;

    // The user is stored in the session.
    private final User user = SessionUser.getInstance().getUser();

    public FavoriteInteractor(FavoriteDataAccessInterface dataAccess,
                              FavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(FavoriteInputData favoriteInputData) {
        List<Integer> favorites = dataAccess.getFavorites(user);

        if (favorites.contains(favoriteInputData.getRecipeId())) {
            favorites.remove(favoriteInputData.getRecipeId());
        } else {
            favorites.add(favoriteInputData.getRecipeId());
        }

        dataAccess.saveFavorites(user);

        final FavoriteOutputData outputData = new FavoriteOutputData(favorites.contains(favoriteInputData.getRecipeId()));
        presenter.prepareSuccessView(outputData);
    }

}