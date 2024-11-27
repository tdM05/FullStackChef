package use_case.search;

/**
 * The input boundary interface for the Search Use Case.
 */
public interface SearchInputBoundary {

    /**
     * Executes the Search Use Case.
     * @param searchInputData the input data
     */
    void execute(SearchInputData searchInputData);
}
