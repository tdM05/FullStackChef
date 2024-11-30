package interface_adapter.grocery_list;

import use_case.grocery_list.GroceryListInputBoundary;

/**
 * The controller for the Grocery List View.
 */
public class GroceryListController {
    private final GroceryListInputBoundary groceryListInputBoundary;

    public GroceryListController(GroceryListInputBoundary groceryListInputBoundary) {
        this.groceryListInputBoundary = groceryListInputBoundary;
    }

    /**
     * Executes the Grocery List use case.
     * We don't need anything since we get it from the api.
     */
    public void execute() {
        groceryListInputBoundary.execute();
    }

    public void switchToSearchView() {
        groceryListInputBoundary.switchToSearchView();
    }
}
