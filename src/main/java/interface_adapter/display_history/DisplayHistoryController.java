package interface_adapter.display_history;

import use_case.display_history.DisplayHistoryInputBoundary;

/**
 * The Controller for the Display History View.
 */
public class DisplayHistoryController {
    private final DisplayHistoryInputBoundary displayHistoryInteractor;

    public DisplayHistoryController(DisplayHistoryInputBoundary displayHistoryInteractor){
        this.displayHistoryInteractor = displayHistoryInteractor;
    }

    /**
     * Executes the Display History Use Case.
     */
    public void execute() {
        displayHistoryInteractor.execute();
    }

    /**
     * Executes the "switch to SearchView" Use Case.
     */
    public void switchToSearchView() {
        displayHistoryInteractor.switchToSearchView();
    }


}
