package view;

import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanController;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanViewModel;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View class for displaying and managing the weekly meal plan.
 */
public class MealPlanView extends JPanel implements PropertyChangeListener {

    private final GenerateMealPlanViewModel viewModel;
    private final JLabel loadingSpinner = new JLabel("Generating Your Meal Plan...", SwingConstants.CENTER);
    private final JPanel contentPanel = new JPanel(new GridLayout(1, 7)); // 7 columns for 7 days
    private GenerateMealPlanController controller;

    private JComboBox<String> dietDropdown;

    public MealPlanView(GenerateMealPlanViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Initialize loading spinner
        loadingSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
        loadingSpinner.setVisible(false); // Initially hidden

        // Add loading spinner and content panel
        add(loadingSpinner, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Add action buttons
        JPanel bottomPanel = new JPanel();

        // Diet dropdown for user selection
        dietDropdown = new JComboBox<>(new String[]{"Gluten Free", "Ketogenic", "Vegetarian", "Lacto-Vegetarian",
                "Ovo-Vegetarian", "Vegan", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30"});
        bottomPanel.add(new JLabel("Dietary Preference:"));
        bottomPanel.add(dietDropdown);

        JButton generateMealPlanButton = new JButton("Generate Meal Plan");
        JButton generateGroceryListButton = new JButton("Generate Grocery List");

        generateMealPlanButton.addActionListener(e -> {
            if (controller != null) {
                String selectedDiet = (String) dietDropdown.getSelectedItem();
                controller.execute(selectedDiet != null ? selectedDiet : "None", LocalDate.now().toString());
            }
        });

        generateGroceryListButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Grocery list generation is not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE));

        bottomPanel.add(generateMealPlanButton);
        bottomPanel.add(generateGroceryListButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize default view with empty day panels
        initializeEmptyPanels();
    }

    /**
     * Initializes the content panel with empty day panels.
     */
    private void initializeEmptyPanels() {
        contentPanel.removeAll();
        for (int i = 0; i < 7; i++) {
            JPanel dayPanel = createDayPanel(LocalDate.now().plusDays(i), new ArrayList<>());
            contentPanel.add(dayPanel);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Sets the controller for generating meal plans.
     *
     * @param controller The GenerateMealPlanController instance.
     */
    public void setMealPlanController(GenerateMealPlanController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "mealPlan":
                updateMealPlan((Map<LocalDate, List<GenerateMealPlanRecipeDto>>) evt.getNewValue());
                break;
            case "isLoading":
                boolean isLoading = (boolean) evt.getNewValue();
                showLoadingSpinner(isLoading);
                break;
            case "errorMessage":
                String errorMessage = (String) evt.getNewValue();
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }

    private void updateMealPlan(Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlanData) {
        contentPanel.removeAll(); // Clear previous content

        for (Map.Entry<LocalDate, List<GenerateMealPlanRecipeDto>> entry : mealPlanData.entrySet()) {
            JPanel dayPanel = createDayPanel(entry.getKey(), entry.getValue());
            contentPanel.add(dayPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDayPanel(LocalDate date, List<GenerateMealPlanRecipeDto> recipes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Display day abbreviation
        String dayAbbreviation = date.getDayOfWeek().toString().substring(0, 3).toUpperCase(); // e.g., MON, TUE
        JLabel dateLabel = new JLabel(dayAbbreviation + " - " + date);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dateLabel);

        // Display recipes
        JPanel recipesPanel = new JPanel();
        recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
        for (GenerateMealPlanRecipeDto recipe : recipes) {
            JLabel recipeLabel = new JLabel(recipe.getTitle());
            recipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            recipesPanel.add(recipeLabel);
        }
        panel.add(recipesPanel);

        // Add Recipe button
        JButton addRecipeButton = new JButton("Add Recipe");
        addRecipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRecipeButton.addActionListener(e -> {
            GenerateMealPlanRecipeDto newRecipe = new GenerateMealPlanRecipeDto(0, "New Recipe");
            recipes.add(newRecipe);
            JLabel recipeLabel = new JLabel(newRecipe.getTitle());
            recipeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            recipesPanel.add(recipeLabel);
            recipesPanel.revalidate();
            panel.repaint();
        });
        panel.add(addRecipeButton);

        // Remove Recipe button
        JButton removeRecipeButton = new JButton("Remove Recipe");
        removeRecipeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeRecipeButton.addActionListener(e -> {
            if (!recipes.isEmpty()) {
                recipes.remove(recipes.size() - 1);
                recipesPanel.remove(recipesPanel.getComponentCount() - 1);
                recipesPanel.revalidate();
                panel.repaint();
            }
        });
        panel.add(removeRecipeButton);

        return panel;
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        contentPanel.setVisible(!show);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GenerateMealPlanViewModel viewModel = new GenerateMealPlanViewModel();
            MealPlanView mealPlanView = new MealPlanView(viewModel);

            JFrame frame = new JFrame("Meal Plan Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(mealPlanView);
            frame.setVisible(true);
        });
    }
}