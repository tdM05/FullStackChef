package interface_adapter.history;

import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryInputData;

/**
 * The controller for managing the history of recipes.
 * It receives user actions and interacts with the use case layer.
 */
public class HistoryController {
    private final HistoryInputBoundary historyInteractor;

    public HistoryController(HistoryInputBoundary historyInteractor) {
        this.historyInteractor = historyInteractor;
    }

    /**
     * Handles the action of adding a recipe to the history.
     * @param recipeId the ID of the recipe
     */
    public void execute(int recipeId){
        final HistoryInputData historyInputData = new HistoryInputData(recipeId);
        historyInteractor.execute(historyInputData);
    }
}
