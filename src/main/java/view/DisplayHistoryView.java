package view;

import entity.CommonRecipe;
import entity.Recipe;
import interface_adapter.check_favorite.CheckFavoriteController;
import interface_adapter.display_history.DisplayHistoryController;
import interface_adapter.display_history.DisplayHistoryState;
import interface_adapter.display_history.DisplayHistoryViewModel;
import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.history.HistoryController;
import use_case.display_history.DisplayHistoryOutputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DisplayHistoryView extends JPanel implements PropertyChangeListener {
    private final String viewName = "displayHistoryView";
    private DisplayRecipeController displayRecipeController;
    private DisplayHistoryController displayHistoryController;
    private CheckFavoriteController checkFavoriteController;
    private HistoryController historyController;
    private DisplayHistoryViewModel viewModel;

    // Components that need to be updated
    private JLabel titleLabel;
    private RecipePanel recipePanel;
    private JTabbedPane tabbedPane;

    public DisplayHistoryView(DisplayHistoryViewModel displayHistoryViewModel) {
        this.viewModel = displayHistoryViewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Set layout for the panel
        setLayout(new BorderLayout());

        // Title label
        titleLabel = new JLabel("Recipe History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setOpaque(true); // To ensure background color is visible
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE); // Contrast text color
        add(titleLabel, BorderLayout.NORTH);

        // Back button panel
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align the button to the left
        JButton backButton = new JButton("Back");

        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            displayHistoryController.switchToSearchView();
        });


        // Tabbed pane
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Loads recipe history recipes from the view model and updates the UI.
     */
    private void loadHistoryRecipes() {
        tabbedPane.removeAll(); // Clear existing tabs

        // Fetch history recipes from the view model
        final DisplayHistoryState state = viewModel.getState();
        List<DisplayHistoryOutputData> outputData = state.getHistoryRecipes();

        List<Recipe> recipes = new ArrayList<>();
        for (DisplayHistoryOutputData data : outputData) {
            Recipe recipe = new CommonRecipe(
                    data.getRecipeId(),
                    data.getRecipeName(),
                    data.getImageUrl(),
                    null,
                    null,
                    null,
                    null,
                    false
            );
            recipes.add(recipe);
        }

        // Create RecipePanel with history of recipes
        recipePanel = new RecipePanel(recipes, displayRecipeController, checkFavoriteController, historyController, viewName);

        // Add RecipePanel to the tabbed pane
        tabbedPane.addTab("Recipes", recipePanel);

        // Refresh the UI
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof DisplayHistoryState) {
            DisplayHistoryState newState = (DisplayHistoryState) evt.getNewValue();
            updateHistoryeRecipes(newState);
        }
    }

    private void updateHistoryeRecipes(DisplayHistoryState newState) {
        loadHistoryRecipes();
    }

    public String getViewName () {
        return viewName;
    }

    public void setDisplayRecipeController(DisplayRecipeController displayRecipeController) {
        this.displayRecipeController = displayRecipeController;
    }

    public void setCheckFavoriteController(CheckFavoriteController checkFavoriteController) {
        this.checkFavoriteController = checkFavoriteController;
    }

    public void setDisplayHistoryController(DisplayHistoryController displayHistoryController) {
        this.displayHistoryController = displayHistoryController;
    }


    public void setHistoryController(HistoryController historyController) {
        this.historyController = historyController;
    }
}
