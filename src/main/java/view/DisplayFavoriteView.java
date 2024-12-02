package view;

import entity.CommonRecipe;
import interface_adapter.check_favorite.CheckFavoriteController;
import interface_adapter.display_favorites.DisplayFavoriteController;
import interface_adapter.display_favorites.DisplayFavoriteState;
import interface_adapter.display_favorites.DisplayFavoriteViewModel;
import entity.Recipe;
import interface_adapter.display_recipe.DisplayRecipeController;
import use_case.display_favorite.DisplayFavoriteOutputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DisplayFavoriteView extends JPanel implements PropertyChangeListener {

    private final String viewName = "displayFavoriteView";
    private DisplayRecipeController displayRecipeController;
    private DisplayFavoriteController displayFavoriteController;
    private CheckFavoriteController checkFavoriteController;
    private DisplayFavoriteViewModel viewModel;

    // Components that need to be updated
    private JLabel titleLabel;
    private RecipePanel recipePanel;
    private JTabbedPane tabbedPane;

    public DisplayFavoriteView(DisplayFavoriteViewModel displayFavoriteViewModel) {
        this.viewModel = displayFavoriteViewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Set layout for the panel
        setLayout(new BorderLayout());

        // Title label
        titleLabel = new JLabel("Favorite", SwingConstants.CENTER);
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
            displayFavoriteController.switchToSearchView();
        });


        // Tabbed pane
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Loads favorite recipes from the view model and updates the UI.
     */
    private void loadFavoriteRecipes() {
        tabbedPane.removeAll(); // Clear existing tabs

        // Fetch favorite recipes from the view model
        final DisplayFavoriteState state = viewModel.getState();
        List<DisplayFavoriteOutputData> outputData = state.getFavoriteRecipes();

        List<Recipe> recipes = new ArrayList<>();
        for (DisplayFavoriteOutputData data : outputData) {
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

        // Create RecipePanel with favorite recipes
        recipePanel = new RecipePanel(recipes, displayRecipeController, checkFavoriteController, viewName);

        // Add RecipePanel to the tabbed pane
        tabbedPane.addTab("Recipes", recipePanel);

        // Refresh the UI
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof DisplayFavoriteState) {
            DisplayFavoriteState newState = (DisplayFavoriteState) evt.getNewValue();
            updateFavoriteRecipes(newState);
        }
    }

    /**
     * Updates the favorite recipes displayed in the UI based on the new state.
     *
     * @param state The new state containing updated favorite recipes.
     */
    private void updateFavoriteRecipes(DisplayFavoriteState state) {
        loadFavoriteRecipes();
    }

    public String getViewName() {
        return viewName;
    }

    public void setDisplayRecipeController(DisplayRecipeController displayRecipeController) {
        this.displayRecipeController = displayRecipeController;
    }

    public void setDisplayFavoriteController(DisplayFavoriteController displayFavoriteController) {
        this.displayFavoriteController = displayFavoriteController;
    }

    public void setCheckFavoriteController(CheckFavoriteController checkFavoriteController) {
        this.checkFavoriteController = checkFavoriteController;
    }
}