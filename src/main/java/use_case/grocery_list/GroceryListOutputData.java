package use_case.grocery_list;

import java.util.List;

/**
 * Output data for the GroceryList use case.
 */
public class GroceryListOutputData {
    private final List<String> groceryList;

    public GroceryListOutputData(List<String> groceryList) {
        this.groceryList = groceryList;
    }

    public List<String> getGroceryList() {
        return groceryList;
    }
}
