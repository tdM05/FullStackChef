package interface_adapter.display_history;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import use_case.display_history.DisplayHistoryOutputBoundary;
import use_case.display_history.DisplayHistoryOutputData;

import java.util.List;

/**
 * The Presenter for the Display History View.
 */
public class DisplayHistoryPresenter implements DisplayHistoryOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;
    private DisplayHistoryViewModel displayHistoryViewModel;

    public DisplayHistoryPresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, DisplayHistoryViewModel displayHistoryViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.displayHistoryViewModel = displayHistoryViewModel;
    }

    @Override
    public void prepareSuccessView(List<DisplayHistoryOutputData> response) {
        final DisplayHistoryState state = displayHistoryViewModel.getState();
        state.setHistoryRecipes(response);
        displayHistoryViewModel.firePropertyChanged();

        viewManagerModel.setState(displayHistoryViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final DisplayHistoryState state = displayHistoryViewModel.getState();
        state.setError(error);
        displayHistoryViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }


}
