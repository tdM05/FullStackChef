package interface_adapter.history;

import interface_adapter.display_recipe.DisplayRecipeViewModel;
import use_case.history.HistoryOutputBoundary;
import use_case.history.HistoryOutputData;

/**
 * The Presenter for the History Use Case.
 */
public class HistoryPresenter implements HistoryOutputBoundary {

    private final DisplayRecipeViewModel displayRecipeViewModel;

    public HistoryPresenter(DisplayRecipeViewModel displayRecipeViewModel) {
        this.displayRecipeViewModel = displayRecipeViewModel;
    }

    /**
     * Prepares the success view for the History related Use Cases.
     *
     * @param outputDataList the output data
     */
    @Override
    public void prepareSuccessView(HistoryOutputData outputDataList) {
        displayRecipeViewModel.updateHistory(outputDataList.getHistory());
        this.displayRecipeViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the History related Use Cases.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        displayRecipeViewModel.updateWithError(errorMessage);
    }


}
