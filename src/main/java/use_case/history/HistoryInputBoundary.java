package use_case.history;

/**
 * The input boundary for the Add History Use Case.
 */
public interface HistoryInputBoundary {

        /**
        * Executes the Add History Use Case.
        * @param historyInputData the input data for the use case
        */
        void execute(HistoryInputData historyInputData);
}
