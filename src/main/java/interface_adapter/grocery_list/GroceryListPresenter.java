package interface_adapter.grocery_list;

import interface_adapter.ViewManagerModel;
import use_case.grocery_list.GroceryListOutputBoundary;
import use_case.grocery_list.GroceryListOutputData;

/**
 * Presenter for the grocery list view.
 */
public class GroceryListPresenter implements GroceryListOutputBoundary {

    private final GroceryListViewModel groceryListViewModel;
    private ViewManagerModel viewManagerModel;

    public GroceryListPresenter(GroceryListViewModel groceryListViewModel, ViewManagerModel viewManagerModel) {
        this.groceryListViewModel = groceryListViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GroceryListOutputData response) {
        final GroceryListState state = groceryListViewModel.getState();
        state.setList(response.getGroceryList());
        groceryListViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final GroceryListState state = groceryListViewModel.getState();
        state.setError(error);
        groceryListViewModel.firePropertyChanged();
    }
}
