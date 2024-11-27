package use_case.favorite_recipe;

import entity.CommonUser;

import java.util.List;

public class FavoriteRecipeInteractor implements FavoriteRecipeInputBoundary {
    private final FavoriteRecipeDataAccessInterface userDataAccessObject;
    private final FavoriteRecipeOutputBoundary userPresenter;

    private final CommonUser user = new CommonUser("testing", "testing");

    public FavoriteRecipeInteractor(FavoriteRecipeDataAccessInterface favoriteRecipeDataAccessInterface,
                                    FavoriteRecipeOutputBoundary favoriteRecipeOutputBoundary) {
        this.userDataAccessObject = favoriteRecipeDataAccessInterface;
        this.userPresenter = favoriteRecipeOutputBoundary;
    }

    @Override
    public void executeFavoriteAction(FavoriteRecipeInputData favoriteRecipeInputData) {
        try {
            List<Integer> favorites = userDataAccessObject.getFavorites(user);

            if (favorites.contains(favoriteRecipeInputData.getRecipeId())) {
                favorites.remove(favoriteRecipeInputData.getRecipeId());
            } else {
                favorites.add(favoriteRecipeInputData.getRecipeId());
            }

            userDataAccessObject.saveFavorites(user);

            final FavoriteRecipeOutputData outputData =
                    new FavoriteRecipeOutputData(favorites.contains(favoriteRecipeInputData.getRecipeId()), false);
            userPresenter.prepareSuccessView(outputData);

        } catch (FavoriteException e) {
            userPresenter.prepareFailView("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void executeIsFavorite(FavoriteRecipeInputData favoriteRecipeInputData) {
        try {
            boolean isFavorite = isFavorite(favoriteRecipeInputData.getRecipeId());
            final FavoriteRecipeOutputData outputData = new FavoriteRecipeOutputData(isFavorite, false);
            userPresenter.prepareSuccessView(outputData);
        } catch (FavoriteException e) {
            userPresenter.prepareFailView("An error occurred while checking favorite status: " + e.getMessage());
        }
    }

    private boolean isFavorite(int recipeId) throws FavoriteException {
        List<Integer> favorites = userDataAccessObject.getFavorites(user);
        return favorites.contains(recipeId);
    }
}