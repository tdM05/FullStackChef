package view;

import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipeViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class DisplayRecipeView extends JPanel implements PropertyChangeListener {

    private DisplayRecipeController controller;
    private final JLabel recipeTitleLabel = new JLabel();
    private final JLabel recipeImageLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea(10, 30);
    private final JTextArea instructionsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();
    private Runnable backButtonListener;

    public DisplayRecipeView(DisplayRecipeViewModel viewModel) {
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        recipeTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recipeImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ingredientsArea.setEditable(false);
        instructionsArea.setEditable(false);
        errorLabel.setForeground(Color.RED);

        add(recipeTitleLabel);
        add(recipeImageLabel);
        add(new JLabel("Ingredients:"));
        add(new JScrollPane(ingredientsArea));
        add(new JLabel("Instructions:"));
        add(new JScrollPane(instructionsArea));
        add(errorLabel);

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(backButton);

        backButton.addActionListener(e -> {
            if (backButtonListener != null) {
                backButtonListener.run();
            }
        });
    }

    public void addBackButtonListener(Runnable listener) {
        this.backButtonListener = listener;
    }

    public void setRecipeController(DisplayRecipeController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateView((DisplayRecipeViewModel) evt.getSource());
        }
    }

    private void updateView(DisplayRecipeViewModel viewModel) {
        // Display title
        recipeTitleLabel.setText(viewModel.getTitle());

        // Load and display image from URL
        if (viewModel.getImageUrl() != null && !viewModel.getImageUrl().isEmpty()) {
            try {
                URL imageUrl = new URL(viewModel.getImageUrl());
                ImageIcon imageIcon = new ImageIcon(imageUrl);
                recipeImageLabel.setIcon(imageIcon);
            } catch (Exception e) {
                recipeImageLabel.setIcon(null);  // Clear image if loading fails
                System.err.println("Error loading image: " + e.getMessage());
            }
        } else {
            recipeImageLabel.setIcon(null);  // Clear image if URL is empty
        }

        // Display ingredients and instructions as before
        ingredientsArea.setText(String.join("\n", viewModel.getIngredients()));
        instructionsArea.setText(String.join("\n", viewModel.getInstructions()));

        // Display error if present
        errorLabel.setText(viewModel.getErrorMessage() != null ? viewModel.getErrorMessage() : "");
    }


    public void loadRecipeDetails(int recipeId) {
        if (controller != null) {
            controller.execute(recipeId);
        }
    }
}
