package view;

import interface_adapter.dietaryrestrictions.DietaryRestrictionController;
import interface_adapter.dietaryrestrictions.DietaryRestrictionViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * View for managing dietary restrictions with a dropdown menu and checkboxes.
 */
public class DietaryRestrictionView extends JPanel {

    private final String viewName = "dietaryRestrictionView";
    private DietaryRestrictionController controller;

    private final List<JCheckBox> checkboxes = new ArrayList<>();
    private final String[] availableDiets = {
            "Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian",
            "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo", "Primal", "Whole30"
    };

    /**
     * Constructs a DietaryRestrictionView.
     *
     * @param viewModel the ViewModel for the dietary restrictions
     */
    public DietaryRestrictionView(DietaryRestrictionViewModel viewModel) {
        setLayout(new BorderLayout());

        // Title Panel
        JLabel titleLabel = new JLabel("Select Your Dietary Restrictions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Center Panel: Dropdown menu with checkboxes
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(centerPanel);

        for (String diet : availableDiets) {
            JCheckBox checkBox = new JCheckBox(diet);
            checkboxes.add(checkBox);
            centerPanel.add(checkBox);
        }

        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel: Save button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        bottomPanel.add(saveButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Save Button Action
        saveButton.addActionListener(e -> handleSaveButton());
    }

    /**
     * Handles the Save button action to collect selected dietary restrictions.
     */
    private void handleSaveButton() {
        List<String> selectedDiets = new ArrayList<>();
        for (JCheckBox checkBox : checkboxes) {
            if (checkBox.isSelected()) {
                selectedDiets.add(checkBox.getText());
            }
        }

        if (controller != null) {
            controller.setDietaryRestrictions(selectedDiets);
            JOptionPane.showMessageDialog(this, "Dietary restrictions saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Controller is not set.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller the DietaryRestrictionController to set
     */
    public void setController(DietaryRestrictionController controller) {
        this.controller = controller;
    }

    /**
     * Gets the name of this view.
     *
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }
}
