package interface_adapter.display_favorites;

import use_case.display_favorite.DisplayFavoriteInputBoundary;

/**
 * Controller for the Display Favorite Use Case.
 */
public class DisplayFavoriteController {
    private final DisplayFavoriteInputBoundary displayFavoriteInteractor;

    public DisplayFavoriteController(DisplayFavoriteInputBoundary displayFavoriteInteractor) {
        this.displayFavoriteInteractor = displayFavoriteInteractor;
    }

    /**
     * Executes the Display Favorite Use Case.
     */
    public void execute() {
        displayFavoriteInteractor.execute();
    }
}
