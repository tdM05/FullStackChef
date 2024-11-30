package use_case.grocery_list;

/**
 * The input boundary for the Grocery list use case.
 */
public interface GroceryListInputBoundary {
    /**
     * Executes the use case.
     */
    void execute();

    void switchToSearchView();
}
