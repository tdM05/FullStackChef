package use_case.grocery_list;

import java.util.List;

/**
 * Input data for the GroceryList use case.
 */
public class GroceryListInputData {

    private final int userId;

    public GroceryListInputData(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

}
