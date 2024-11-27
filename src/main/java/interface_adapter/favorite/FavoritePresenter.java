package interface_adapter.favorite;

import interface_adapter.ViewManagerModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

public class FavoritePresenter implements FavoriteOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final FavoriteViewModel favoriteViewModel;

    public FavoritePresenter(ViewManagerModel viewManagerModel, FavoriteViewModel favoriteViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.favoriteViewModel = favoriteViewModel;
    }

    @Override
    public void prepareSuccessView(FavoriteOutputData response) {
        final FavoriteState state = favoriteViewModel.getState();
        state.setFavorite(response.getFavorite());
        favoriteViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final FavoriteState state = favoriteViewModel.getState();
        state.setError(error);
        favoriteViewModel.firePropertyChanged();
    }
}
