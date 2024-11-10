package view;

import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipeViewModel;
import interface_adapter.display_recipe.DisplayRecipeState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class DisplayRecipeView extends JPanel implements PropertyChangeListener {

    private DisplayRecipeController controller;  // Reference to the controller
    private final JLabel recipeTitleLabel = new JLabel();
    private final JLabel recipeImageLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea(10, 30);
    private final JTextArea instructionsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();

    public DisplayRecipeView(DisplayRecipeViewModel viewModel) {
        viewModel.addPropertyChangeListener(this);

        // Configure layout and styles
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        recipeTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recipeImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientsArea.setEditable(false);
        instructionsArea.setEditable(false);
        errorLabel.setForeground(Color.RED);

        // Add components to the view
        add(recipeTitleLabel);
        add(recipeImageLabel);
        add(new JLabel("Ingredients:"));
        add(new JScrollPane(ingredientsArea));
        add(new JLabel("Instructions:"));
        add(new JScrollPane(instructionsArea));
        add(errorLabel);
    }

    /**
     * Sets the controller for this view.
     * @param controller the DisplayRecipeController to set
     */
    public void setRecipeController(DisplayRecipeController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Ensure we're reacting to a DisplayRecipeState update
        if (evt.getNewValue() instanceof DisplayRecipeState) {
            DisplayRecipeState state = (DisplayRecipeState) evt.getNewValue();
            updateView(state);
        }
    }

    private void updateView(DisplayRecipeState state) {
        // Update title
        recipeTitleLabel.setText(state.getTitle());

        // Update image
        try {
            if (state.getImageUrl() != null && !state.getImageUrl().isEmpty()) {
                URL imageUrl = new URL(state.getImageUrl());
                recipeImageLabel.setIcon(new ImageIcon(imageUrl));
            } else {
                recipeImageLabel.setIcon(null);  // Clear image if not available
            }
        } catch (Exception e) {
            recipeImageLabel.setIcon(null);  // Clear image if loading fails
        }

        // Update ingredients
        StringBuilder ingredientsText = new StringBuilder();
        if (state.getIngredients() != null) {
            for (String ingredient : state.getIngredients()) {
                ingredientsText.append(ingredient).append("\n");
            }
        }
        ingredientsArea.setText(ingredientsText.toString());

        // Update instructions
        StringBuilder instructionsText = new StringBuilder();
        if (state.getInstructions() != null) {
            for (String instruction : state.getInstructions()) {
                instructionsText.append(instruction).append("\n");
            }
        }
        instructionsArea.setText(instructionsText.toString());

        // Display any error messages
        errorLabel.setText(state.getDisplayError() != null ? state.getDisplayError() : "");
    }

    /**
     * An example method that interacts with the controller.
     * This could be called from an action, such as a button click.
     */
    private void loadRecipeDetails(int recipeId) {
        if (controller != null) {
            controller.execute(recipeId);  // Use the controller to load recipe details
        }
    }
}
