package view;

import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.grocery_list.GroceryListState;
import interface_adapter.grocery_list.GroceryListViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The View for the grocery list.
 */
public class GroceryListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "grocery list";
    private final GroceryListViewModel groceryListViewModel;

    private final JButton returnButton;
    private final List<JLabel> groceryList;

    private GroceryListController controller;

    public GroceryListView(GroceryListViewModel groceryListViewModel) {
        this.groceryListViewModel = groceryListViewModel;
        this.groceryListViewModel.addPropertyChangeListener(this);
        this.returnButton = new JButton("Return");
        this.groceryList = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with vertical alignment
        this.add(returnButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final GroceryListState state = (GroceryListState) evt.getNewValue();
        refreshPage(state);
    }

    private void refreshPage(GroceryListState state) {
        this.removeAll();
        this.add(returnButton);
        this.groceryList.clear();

        for (String item : state.getList()) {
            final JLabel label = new JLabel(item);
            this.groceryList.add(label);
            this.add(label);
        }

        this.revalidate();
        this.repaint();
    }

    public void setController(GroceryListController controller) {
        this.controller = controller;
    }

    public void updateGroceryList() {
        controller.execute();
    }
}
