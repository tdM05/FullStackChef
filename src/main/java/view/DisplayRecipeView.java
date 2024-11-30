package view;

import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class DisplayRecipeView extends JPanel implements PropertyChangeListener {

    private final String viewName = "displayRecipeView";

    private DisplayRecipeController controller;
    private final JLabel recipeTitleLabel = new JLabel();
    private final JLabel recipeImageLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea(10, 30);
    private final JTextArea instructionsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();
    private final DisplayRecipeViewModel displayRecipeViewModel;

    private FavoriteButton favoriteButton;

    public DisplayRecipeView(DisplayRecipeViewModel displayRecipeViewModel) {
        this.displayRecipeViewModel = displayRecipeViewModel;

        displayRecipeViewModel.addPropertyChangeListener(this);
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

        favoriteButton= new FavoriteButton();
        favoriteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(favoriteButton);

        // Add an ActionListener to respond to state changes
        favoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (favoriteButton.isSelected()) {
                    System.out.println("Recipe favorited");
                    // Add code to handle favoriting the recipe
                } else {
                    System.out.println("Recipe unfavorited");
                    // Add code to handle unfavoriting the recipe
                }
            }
        });

        final JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(backButton);

        backButton.addActionListener(e -> {
                controller.switchToSearchView();

        });
    }

    public void setDisplayRecipeController(DisplayRecipeController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof DisplayRecipeViewModel) {
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


        if (viewModel.getIsFavorite()) {
            System.out.println("Recipe is a favorite");
            favoriteButton.setSelected(true);
        } else {
            System.out.println("Recipe is not a favorite");
            favoriteButton.setSelected(false);
        }

        // Display error if present
        errorLabel.setText(viewModel.getErrorMessage() != null ? viewModel.getErrorMessage() : "");
    }

    public void loadRecipeDetails(int recipeId) {
        if (controller != null) {
            controller.execute(recipeId);
        }
    }

    public String getViewName() {
        return viewName;
    }
}
