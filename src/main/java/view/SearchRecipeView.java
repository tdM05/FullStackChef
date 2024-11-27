package view;

import interface_adapter.ViewManagerModel;

import interface_adapter.search_recipe.SearchRecipeViewModel;
import interface_adapter.search_recipe.SearchRecipeController;

import use_case.search_recipe.SearchRecipeOutputData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for the Search Recipe Use Case.
 */
public class SearchRecipeView extends JPanel {

    private final SearchRecipeViewModel viewModel;
    private SearchRecipeController controller;

    private Profile profile;
    private JTextField searchBar;
    private JPanel topPanel;
    private JPanel centerPanel;

    // Adjust the roundness
    private final int cornerRadius = 50;

    private ViewManagerModel viewManagerModel;

    /**
     * Constructor for the SearchRecipeView.
     * @param viewModel the ViewModel for the Search Recipe Use Case
     * @param viewManagerModel the ViewManagerModel
     */
    public SearchRecipeView(SearchRecipeViewModel viewModel,
                            ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());
        this.viewManagerModel = viewManagerModel;
        loadScreen();
        registerPropertyChangeListeners();
    }

    private void loadScreen() {

        // Create the profile component
        profile = new Profile(this.viewManagerModel);

        // Create the search bar with rounded corners and shadow
        searchBar = createSearchBar();

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

        // Add panels to the view
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Force UI to update
        revalidate();
        repaint();
    }

    private JTextField createSearchBar() {

        JTextField searchBar = new JTextField("Search for recipes...") {
            private Shape shape;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 30)); // Shadow color
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the border
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the search icon
                int circleSize = 12;
                int handleLength = 6;
                int iconSize = circleSize + handleLength - 2; // Total icon size (approximate)
                int iconX = 20; // Left padding
                int iconY = (getHeight() - iconSize) / 2 - 4; // Center the icon vertically

                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.GRAY);

                // Draw the circle part of the magnifying glass
                int circleX = iconX;
                int circleY = iconY;

                g2.drawOval(circleX, circleY, circleSize, circleSize);

                // Draw the handle
                int lineX1 = circleX + circleSize - 1;
                int lineY1 = circleY + circleSize - 1;
                int lineX2 = lineX1 + handleLength;
                int lineY2 = lineY1 + handleLength;

                g2.drawLine(lineX1, lineY1, lineX2, lineY2);

                // Set clipping area for text
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius));

                // Draw the text
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public Insets getInsets() {
                return new Insets(10, 60, 10, 20); // Top, Left, Bottom, Right
            }

            @Override
            public void setBorder(Border border) {
                // No border
            }

            @Override
            public boolean contains(int x, int y) {
                if (shape == null || !shape.getBounds().equals(getBounds())) {
                    shape = new RoundRectangle2D.Float(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);
                }
                return shape.contains(x, y);
            }
        };

        // Set search bar properties
        searchBar.setPreferredSize(new Dimension(800, 60));
        searchBar.setFont(new Font("SansSerif", Font.ITALIC, 24));
        searchBar.setForeground(Color.GRAY);
        searchBar.setBackground(Color.WHITE);
        searchBar.setOpaque(false);

        // Placeholder text logic
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().trim().equals("Search for recipes...")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setFont(new Font("SansSerif", Font.PLAIN, 24)); // Normal font
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().trim().isEmpty()) {
                    searchBar.setText("Search for recipes...");
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setFont(new Font("SansSerif", Font.ITALIC, 24)); // Italic font for placeholder
                }
            }
        });

        // Add action listener for Enter key
        searchBar.addActionListener(e -> onSearch());

        return searchBar;
    }

    private void onSearch() {
        if (controller != null) {
            String query = searchBar.getText();
            controller.searchRecipe(query);
        }
    }

    private void registerPropertyChangeListeners() {
        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
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
        String error = viewModel.getError();
        if (error != null && !error.isEmpty()) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResults() {
        List<SearchRecipeOutputData> recipes = viewModel.getRecipes();
        displayRecipes(recipes);
    }

    private void displayRecipes(List<SearchRecipeOutputData> recipes) {
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

    public void setRecipeController(SearchRecipeController controller) {
        this.controller = controller;
    }
}