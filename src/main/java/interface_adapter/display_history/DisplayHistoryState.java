package interface_adapter.display_history;

import use_case.display_history.DisplayHistoryOutputData;

import java.util.List;

/**
 * The View Model for the Display History View.
 */
public class DisplayHistoryState {
    private List<DisplayHistoryOutputData> historyRecipes;
    private String error;

    public List<DisplayHistoryOutputData> getHistoryRecipes() {
        return historyRecipes;
    }

    public void setHistoryRecipes(List<DisplayHistoryOutputData> historyRecipes) {
        this.historyRecipes = historyRecipes;
    }

    public void setError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
