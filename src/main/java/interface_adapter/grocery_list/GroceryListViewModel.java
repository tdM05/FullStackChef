package interface_adapter.grocery_list;

import interface_adapter.ViewModel;

/**
 * The View Model for the Grocery List View.
 */
public class GroceryListViewModel extends ViewModel<GroceryListState> {
    public GroceryListViewModel() {
        super("grocery list");
        setState(new GroceryListState());
    }

}
