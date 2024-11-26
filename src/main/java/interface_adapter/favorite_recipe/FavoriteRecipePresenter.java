package interface_adapter.favorite_recipe;

import interface_adapter.ViewManagerModel;
import use_case.favorite_recipe.FavoriteRecipeOutputBoundary;
import use_case.favorite_recipe.FavoriteRecipeOutputData;

public class FavoriteRecipePresenter implements FavoriteRecipeOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final FavoriteRecipeViewModel favoriteRecipeViewModel;

    public FavoriteRecipePresenter(ViewManagerModel viewManagerModel,FavoriteRecipeViewModel favoriteRecipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.favoriteRecipeViewModel = favoriteRecipeViewModel;
    }

    @Override
    public void prepareSuccessView(FavoriteRecipeOutputData response) {
        final FavoriteRecipeState state = favoriteRecipeViewModel.getState();
        state.setFavorite(response.getFavorite());
        favoriteRecipeViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final FavoriteRecipeState state = favoriteRecipeViewModel.getState();
        state.setError(error);
        favoriteRecipeViewModel.firePropertyChanged();
    }
}
