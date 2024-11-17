package view;

import data_access.grocery_list.GroceryListDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.grocery_list.GroceryListPresenter;
import interface_adapter.grocery_list.GroceryListViewModel;
import org.junit.Test;
import use_case.grocery_list.GroceryListDataAccessInterface;
import use_case.grocery_list.GroceryListInteractor;
import use_case.grocery_list.GroceryListOutputBoundary;
import use_case.grocery_list.GroceryListInputBoundary;

import javax.swing.*;

public class GroceryListEndToEnd {
    public static void main(String[] args) {
        // add the view to the frame
        GroceryListViewModel groceryListViewModel = new GroceryListViewModel();
        JPanel groceryListView = new GroceryListView(groceryListViewModel);
        JFrame frame = new JFrame("test");
        frame.setContentPane(groceryListView);
        frame.setSize(800, 600);
        frame.setVisible(true);

        // add the use case
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final GroceryListOutputBoundary outputBoundary = new GroceryListPresenter(groceryListViewModel, viewManagerModel);

        GroceryListDataAccessInterface dataAccess = new GroceryListDataAccessObject();
        GroceryListOutputBoundary presenter = new GroceryListPresenter(groceryListViewModel, viewManagerModel);
        final GroceryListInputBoundary inputBoundary = new GroceryListInteractor(dataAccess, presenter);

        // add the controller
        GroceryListController controller = new GroceryListController(inputBoundary);
        // execute use case
        controller.execute();
    }
}