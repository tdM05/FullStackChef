package view;

import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipeState;
import interface_adapter.display_recipe.DisplayRecipeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for displaying a recipe's details.
 */
public class DisplayRecipeView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "display recipe";
    private final DisplayRecipeViewModel displayRecipeViewModel;

    private final JLabel recipeNameLabel = new JLabel();
    private final JTextArea ingredientsArea = new JTextArea(10, 30);
    private final JTextArea instructionsArea = new JTextArea(10, 30);
    private final JLabel errorLabel = new JLabel();

    private DisplayRecipeController displayRecipeController;

    public DisplayRecipeView(DisplayRecipeViewModel displayRecipeViewModel) {
        this.displayRecipeViewModel = displayRecipeViewModel;
        this.displayRecipeViewModel.addPropertyChangeListener(this);

        final JLabel titleLabel = new JLabel("Recipe Details");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ingredientsArea.setEditable(false);
        instructionsArea.setEditable(false);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(titleLabel);
        this.add(recipeNameLabel);
        this.add(new JScrollPane(ingredientsArea));
        this.add(new JScrollPane(instructionsArea));
        this.add(errorLabel);

        errorLabel.setForeground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Placeholder for any button actions if needed in the future
        System.out.println("Action performed: " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DisplayRecipeState state = (DisplayRecipeState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(DisplayRecipeState state) {
        recipeNameLabel.setText("Recipe: " + state.getRecipeName());
        ingredientsArea.setText(state.getIngredients());
        instructionsArea.setText(state.getInstructions());
        errorLabel.setText(state.getDisplayError());
    }

    public String getViewName() {
        return viewName;
    }

    public void setDisplayRecipeController(DisplayRecipeController displayRecipeController) {
        this.displayRecipeController = displayRecipeController;
    }
}

