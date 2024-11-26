package interface_adapter.favorite_recipe;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Favorite Recipe Use Case.
 * It holds the state of the data to be displayed in the view.
 */
public class FavoriteRecipeViewModel extends ViewModel<FavoriteRecipeState> {

    public FavoriteRecipeViewModel() {
        super("favorite recipe");
        setState(new FavoriteRecipeState()); // Initialize with a default state
    }

    /**
     * Updates the state of the ViewModel to reflect a successful action.
     *
     * @param isFavorite whether the recipe is now a favorite
     */
    public void updateFavoriteState(boolean isFavorite) {
        FavoriteRecipeState state = getState();
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
        FavoriteRecipeState state = getState();
        state.setFavorite(false);
        state.setError(errorMessage);
        firePropertyChanged("state");
    }
}