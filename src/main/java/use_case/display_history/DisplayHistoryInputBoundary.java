package use_case.display_history;

/**
 * The input boundary for the Display History Use Case.
 */
public interface DisplayHistoryInputBoundary {

        /**
        * Executes the Display History Use Case.
        */
        void execute();

        /**
        * Executes the "switch to SearchView" Use Case.
        */
        void switchToSearchView();

}
