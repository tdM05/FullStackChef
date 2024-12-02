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

    public FavoriteInteractor(FavoriteDataAccessInterface dataAccess,
                              FavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(FavoriteInputData favoriteInputData) {
        User user = SessionUser.getInstance().getUser();

        if (user == null) {
            presenter.prepareFailView("Error with retrieving user");
            return;
        }

        List<Integer> favorites = dataAccess.getFavorites(user);

        System.out.println(favoriteInputData.getRecipeId());

        if (favorites.contains(favoriteInputData.getRecipeId())) {
            favorites.remove((Integer) favoriteInputData.getRecipeId());
        } else {
            favorites.add(0,favoriteInputData.getRecipeId());
        }

        System.out.println("Favorites: " + favorites);

        dataAccess.saveFavorites(user, favorites);

        List <Integer> updatedFavorites = dataAccess.getFavorites(user);

        System.out.println("Updated Favorites: " + updatedFavorites);

        final FavoriteOutputData outputData = new FavoriteOutputData(favorites.contains(favoriteInputData.getRecipeId()));

        presenter.prepareSuccessView(outputData);
    }

}