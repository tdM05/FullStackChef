package interface_adapter.display_favorites;

import interface_adapter.ViewModel;

/**
 * The View Model for the Display Favorite View.
 */
public class DisplayFavoriteViewModel extends ViewModel<DisplayFavoriteState> {

    public DisplayFavoriteViewModel(){
        super("displayFavoriteView");
        setState(new DisplayFavoriteState());
    }
}
