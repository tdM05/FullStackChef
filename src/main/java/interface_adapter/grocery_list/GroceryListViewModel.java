package interface_adapter.grocery_list;

import data_access.Constants;
import interface_adapter.ViewModel;

/**
 * The View Model for the Grocery List View.
 */
public class GroceryListViewModel extends ViewModel<GroceryListState> {
    public GroceryListViewModel() {
        super(Constants.GROCERY_LIST_VIEW);
        setState(new GroceryListState());
    }

}
