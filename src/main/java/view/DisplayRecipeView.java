package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
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

    private DisplayRecipeController controller;
    private final JLabel recipeTitleLabel = new JLabel();
    private final JLabel recipeImageLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea(10, 30);
    private final JTextArea instructionsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();
    private ViewManagerModel viewManagerModel;

    private FavoriteButton favoriteButton;

    public DisplayRecipeView(DisplayRecipeViewModel viewModel,
                             ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);

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
            // This causes the view to switch to the main page
            ViewManagerState state = new ViewManagerState(Constants.SEARCH_VIEW, null);
            viewManagerModel.setState(state);
            viewManagerModel.firePropertyChanged();
        });
    }

    private JButton createFavoriteButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));

        // Load the heart icon image
        try {
            // Replace "heart.png" with the path to your heart image
            ImageIcon heartIcon = new ImageIcon(getClass().getResource("/resources/heart.png"));
            // Optionally, scale the icon to fit the button
            Image image = heartIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            heartIcon = new ImageIcon(image);
            button.setIcon(heartIcon);
        } catch (Exception e) {
            System.err.println("Error loading heart icon: " + e.getMessage());
        }

        // Remove button border and background for a cleaner look
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        // The button doesn't do anything yet

        return button;
    }

    public void setRecipeController(DisplayRecipeController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof DisplayRecipeViewModel) {
            updateView((DisplayRecipeViewModel) evt.getSource());
        }
        if (evt.getNewValue() instanceof ViewManagerState) {
            final ViewManagerState state = (ViewManagerState) evt.getNewValue();
            if (state.getViewName().equals(Constants.DISPLAY_RECIPE_VIEW)) {
                // context must be an integer
                final int ctx = (int) state.getContext();
                loadRecipeDetails(ctx);
            }
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
