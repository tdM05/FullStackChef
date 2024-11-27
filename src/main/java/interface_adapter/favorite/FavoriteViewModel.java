package interface_adapter.favorite;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Favorite Recipe Use Case.
 * It holds the state of the data to be displayed in the view.
 */
public class FavoriteViewModel extends ViewModel<FavoriteState> {

    public FavoriteViewModel() {
        super("favorite recipe");
        setState(new FavoriteState()); // Initialize with a default state
    }

    /**
     * Updates the state of the ViewModel to reflect a successful action.
     *
     * @param isFavorite whether the recipe is now a favorite
     */
    public void updateFavoriteState(boolean isFavorite) {
        FavoriteState state = getState();
        state.setFavorite(isFavorite);
        state.setError(null);
        firePropertyChanged("state");
    }

    /**
     * Updates the state of the ViewModel to reflect an error.
     *
     * @param errorMessage the error message to display
     */
    public void updateWithError(String errorMessage) {
        FavoriteState state = getState();
        state.setFavorite(false);
        state.setError(errorMessage);
        firePropertyChanged("state");
    }
}