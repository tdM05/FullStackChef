package interface_adapter.favorite;

import interface_adapter.display_recipe.DisplayRecipeViewModel;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;

/**
 * The Presenter for the Favorite Recipe Use Case.
 */
public class FavoritePresenter implements FavoriteOutputBoundary {

    private final DisplayRecipeViewModel displayRecipeViewModel;

    public FavoritePresenter(DisplayRecipeViewModel displayRecipeViewModel) {
        this.displayRecipeViewModel = displayRecipeViewModel;
    }


    /**
     * Prepares the success view for the Favorite related Use Cases.
     *
     * @param outputDataList the output data
     */

    @Override
    public void prepareSuccessView(FavoriteOutputData outputDataList) {
        displayRecipeViewModel.updateFavoriteStatus(outputDataList.getFavorite());
        this.displayRecipeViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Favorite related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        displayRecipeViewModel.updateWithError(errorMessage);
    }
}
