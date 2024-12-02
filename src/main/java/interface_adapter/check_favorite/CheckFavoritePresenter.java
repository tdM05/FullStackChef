package interface_adapter.check_favorite;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_recipe.DisplayRecipeState;
import interface_adapter.display_recipe.DisplayRecipeViewModel;
import interface_adapter.search.SearchViewModel;
import use_case.check_favorite.CheckFavoriteOutputBoundary;
import use_case.check_favorite.CheckFavoriteOutputData;

/**
 * The Presenter for the Check Favorite Use Case.
 */
public class CheckFavoritePresenter implements CheckFavoriteOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SearchViewModel searchViewModel;
    private final DisplayRecipeViewModel displayRecipeViewModel;

    public CheckFavoritePresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, DisplayRecipeViewModel displayRecipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.displayRecipeViewModel = displayRecipeViewModel;
    }


    @Override
    public void prepareSuccessView(CheckFavoriteOutputData outputData) {
        System.out.println("CheckFavoritePresenter updating ViewModel with favorite status");

        displayRecipeViewModel.updateFavoriteStatus(outputData.getFavorite());
        System.out.println("DisplayRecipeViewModel favorite status: " + displayRecipeViewModel.getIsFavorite());
        final DisplayRecipeState displayRecipeState = displayRecipeViewModel.getState();
        this.displayRecipeViewModel.setState(displayRecipeState);
        this.displayRecipeViewModel.firePropertyChanged();

        System.out.println("Jungkook");
        System.out.println("Recipe is not a favorite");
        this.viewManagerModel.setState(displayRecipeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }


    @Override
    public void prepareFailView(String errorMessage) {
        displayRecipeViewModel.updateWithError(errorMessage);
    }
}
