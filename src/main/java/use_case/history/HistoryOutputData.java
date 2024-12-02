package use_case.history;

/**
 * The Output Data for the Add History Recipe Use Case.
 */
public class HistoryOutputData {
    private final boolean history;

    public HistoryOutputData(boolean history) {
        this.history = history;
    }

    public boolean getHistory() {
        return history;
    }
}
