package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
//import interface_adapter.ViewManagerState;
import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.grocery_list.GroceryListState;
import interface_adapter.grocery_list.GroceryListViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The View for the grocery list.
 */
public class GroceryListView extends JPanel implements PropertyChangeListener {
    private final String viewName = Constants.GROCERY_LIST_VIEW;
    private final GroceryListViewModel groceryListViewModel;

    private final JButton returnButton;
    private final List<JLabel> groceryList;

    private GroceryListController controller;


    /**
     * Creates a new GroceryListView.
     *@param groceryListViewModel The view model for the grocery list.
     */
    public GroceryListView(GroceryListViewModel groceryListViewModel) {
        this.groceryListViewModel = groceryListViewModel;
        this.groceryListViewModel.addPropertyChangeListener(this);
        this.returnButton = new JButton("Return");
        returnButton.addActionListener(evt -> {
            controller.switchToSearchView();
        });
/*        returnButton.addActionListener(evt -> {
            System.out.println("Return button pressed");
            final ViewManagerState state = new ViewManagerState(Constants.SEARCH_VIEW, null);
            viewManagerModel.setState(state);
            viewManagerModel.firePropertyChanged();
        }
        );*/

        this.groceryList = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout to BoxLayout with vertical alignment
        this.add(returnButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof GroceryListState) {
            final GroceryListState state = (GroceryListState) evt.getNewValue();
            refreshPage(state);
        }
/*        if (evt.getNewValue() instanceof ViewManagerState) {
            final ViewManagerState state = (ViewManagerState) evt.getNewValue();
            if (state.getViewName().equals(Constants.GROCERY_LIST_VIEW)) {
                updateGroceryList();
            }
        }*/
    }

    private void refreshPage(GroceryListState state) {
        this.removeAll();
        this.add(returnButton);
        this.groceryList.clear();

        final JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (String item : state.getList()) {
            final JLabel label = new JLabel(item);
            this.groceryList.add(label);
            listPanel.add(label);
        }

        final JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane);

        this.revalidate();
        this.repaint();
    }

    public String getViewName() {
        return viewName;
    }
    public void setController(GroceryListController controller) {
        this.controller = controller;
    }

    public void updateGroceryList() {
        controller.execute();
    }
}
