package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchController;

import use_case.search.SearchOutputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for the Search Recipe Use Case.
 */
public class SearchView extends JPanel {

    private final String viewName = "searchView";
    private final SearchViewModel searchViewModel;
    private SearchController controller;

    private JPanel topPanel;
    private JPanel centerPanel;

    private Profile profile;
    private SearchBar searchBar;

    private ViewManagerModel viewManagerModel;

    /**
     * Constructor for the SearchView.
     * @param searchViewModel the ViewModel for the Search Recipe Use Case
     */
    public SearchView(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
        loadScreen();
        registerPropertyChangeListeners();
    }

    private void loadScreen() {

        // Create the profile component
        profile = new Profile(this.viewManagerModel);

        // Instantiate the SearchBar
        searchBar = new SearchBar("Search for recipes...");
        searchBar.addActionListener(e -> onSearch());


        // Create a panel to hold the search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchBar);

        // Center the search panel in the centerPanel
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(searchPanel);

        // The top panel will hold both the profile and search bar initially
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Add padding
        topPanel.add(profile, BorderLayout.LINE_END);

        // Set the layout and add components
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Force UI to update
        revalidate();
        repaint();
    }

    private void onSearch() {
        if (controller != null) {
            String query = searchBar.getText();
            controller.execute(query);
        }
    }

    private void registerPropertyChangeListeners() {
        searchViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("recipes".equals(evt.getPropertyName())) {
                    updateResults();
                } else if ("error".equals(evt.getPropertyName())) {
                    updateError();
                }
            }
        });
    }

    private void updateError() {
        String error = searchViewModel.getError();
        if (error != null && !error.isEmpty()) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResults() {
        List<SearchOutputData> recipes = searchViewModel.getRecipes();
        displayRecipes(recipes);
    }

    private void displayRecipes(List<SearchOutputData> recipes) {
        // Remove all existing components from the center panel
        centerPanel.removeAll();

        // Create a new panel for the top search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchBar);

        // Update the top panel
        topPanel.removeAll();
        topPanel.add(profile, BorderLayout.LINE_END);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        // Create the recipes panel
        RecipePanel recipesPanel = new RecipePanel(recipes, viewManagerModel);

        // Add the recipes panel to the center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(recipesPanel, BorderLayout.CENTER);

        // Refresh the UI
        revalidate();
        repaint();
    }

    public void setSearchController(SearchController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }
}