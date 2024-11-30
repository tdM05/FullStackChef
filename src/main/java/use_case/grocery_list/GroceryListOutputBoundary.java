package use_case.grocery_list;

public interface GroceryListOutputBoundary {
    /**
     * Prepares the view to display the grocery list.
     * @param outputData The output data from the use case.
     */
    void prepareSuccessView(GroceryListOutputData outputData);
    /**
     * Prepares the view to display an error message.
     * @param errorMessage The error message to display.
     */

    void prepareFailView(String errorMessage);

    void switchToSearchView();
}
