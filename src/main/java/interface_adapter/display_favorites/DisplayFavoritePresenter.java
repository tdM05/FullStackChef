package interface_adapter.display_favorites;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import use_case.display_favorite.DisplayFavoriteOutputBoundary;
import use_case.display_favorite.DisplayFavoriteOutputData;

import java.util.List;

/**
 * The Presenter for the Display Favorite View.
 */

public class DisplayFavoritePresenter implements DisplayFavoriteOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;
    private DisplayFavoriteViewModel displayFavoriteViewModel;

    public DisplayFavoritePresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, DisplayFavoriteViewModel displayFavoriteViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.displayFavoriteViewModel = displayFavoriteViewModel;
    }

    @Override
    public void prepareSuccessView(List<DisplayFavoriteOutputData> response) {
        final DisplayFavoriteState state = displayFavoriteViewModel.getState();
        state.setFavoriteRecipes(response);
        displayFavoriteViewModel.firePropertyChanged();

        viewManagerModel.setState(displayFavoriteViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final DisplayFavoriteState state = displayFavoriteViewModel.getState();
        state.setError(error);
        displayFavoriteViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
