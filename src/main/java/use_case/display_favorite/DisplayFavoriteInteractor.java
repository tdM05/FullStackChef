package use_case.display_favorite;

import entity.CommonUser;
import use_case.favorite_recipe.FavoriteRecipeDataAccessInterface;

import java.util.List;

public class DisplayFavoriteInteractor implements DisplayFavoriteInputBoundary {

    private final FavoriteRecipeDataAccessInterface userDataAccessObject;
    private final DisplayFavoriteOutputBoundary userPresenter;

    private final CommonUser user = new CommonUser("testing", "testing");

    public DisplayFavoriteInteractor(FavoriteRecipeDataAccessInterface favoriteRecipeDataAccessInterface,
                                     DisplayFavoriteOutputBoundary displayFavoriteOutputBoundary) {
        this.userDataAccessObject = favoriteRecipeDataAccessInterface;
        this.userPresenter = displayFavoriteOutputBoundary;
    }

    @Override
    public void execute() {

        List<Integer> favorites = userDataAccessObject.getFavorites(user);

    }
}
