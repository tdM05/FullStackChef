package app;

import data_access.Constants;
import data_access.RecipeDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.grocery_list.GroceryListPresenter;
import interface_adapter.grocery_list.GroceryListViewModel;
import use_case.grocery_list.GroceryListDataAccessInterface;
import use_case.grocery_list.GroceryListInteractor;
import use_case.grocery_list.GroceryListOutputBoundary;
import view.GroceryListView;

import javax.swing.*;
import java.awt.*;

/**
 * Builder for the Grocery List app.
 */
public class GroceryListAppBuilder {
    private GroceryListDataAccessInterface groceryListDAO;
    private GroceryListViewModel groceryListViewModel = new GroceryListViewModel();
    private GroceryListView groceryListView;
    private GroceryListInteractor groceryListInteractor;
    private ViewManagerModel viewManagerModel = new ViewManagerModel();

    /**
     * Adds the Grocery List DAO to the app.
     * @param dao the Grocery List DAO
     * @return this
     */
    public GroceryListAppBuilder addGroceryListDAO(GroceryListDataAccessInterface dao) {
        this.groceryListDAO = dao;
        return this;
    }

    /**
     * Adds the Grocery List view to the app.
     * @return this
     */
    public GroceryListAppBuilder addGroceryListView() {
        viewManagerModel = new ViewManagerModel();
        groceryListViewModel = new GroceryListViewModel();
        groceryListView = new GroceryListView(groceryListViewModel);
        return this;
    }

    /**
     * Adds the Grocery List use case.
     * @return this
     * @throws IllegalStateException if the Grocery List view has not been added.
     */
    public GroceryListAppBuilder addGroceryListUseCase() throws IllegalStateException {
        final GroceryListOutputBoundary output = new GroceryListPresenter(groceryListViewModel, viewManagerModel);
        groceryListInteractor = new GroceryListInteractor(groceryListDAO, output);

        final GroceryListController groceryListController = new GroceryListController(groceryListInteractor);

        if (groceryListView == null) {
            throw new IllegalStateException("You must add the Grocery List view before adding the use case.");
        }

        groceryListView.setController(groceryListController);
        return this;
    }

    public GroceryListView getGroceryListView() {
        return groceryListView;
    }

    /**
     * Builds the Grocery List app into a JFrame.
     * @return the Grocery List app
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Grocery List");
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);

        frame.add(groceryListView);
        return frame;
    }
}
