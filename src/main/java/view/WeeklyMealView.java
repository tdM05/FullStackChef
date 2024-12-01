package view;

import data_access.Constants;
import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.mealplan.generate_mealplan.WeeklyMealController;
import interface_adapter.mealplan.generate_mealplan.WeeklyMealState;
import interface_adapter.mealplan.generate_mealplan.WeeklyMealViewModel;
import interface_adapter.mealplan.update_meals.UpdateMealsState;
import interface_adapter.mealplan.update_meals.UpdateMealsViewModel;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * View class for displaying and managing the weekly meal plan.
 */
public class WeeklyMealView extends JPanel implements PropertyChangeListener {

    private final WeeklyMealViewModel viewModel;
    private final UpdateMealsViewModel updateMealsViewModel;
    private final JLabel loadingSpinner = new JLabel("Generating Your Meal Plan...", SwingConstants.CENTER);
    private final JPanel contentPanel = new JPanel(new GridLayout(1, 7, 5, 5)); // 7 columns for 7 days
    private final JLabel weekLabel = new JLabel("", SwingConstants.CENTER); // Displays the week range
    private GroceryListController groceryListController;
    private WeeklyMealController controller;
    private LocalDate startDate; // Tracks the current week's start date
    private Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlanData;

    public WeeklyMealView(WeeklyMealViewModel viewModel,
                          UpdateMealsViewModel updateMealsViewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.updateMealsViewModel = updateMealsViewModel;
        this.updateMealsViewModel.addPropertyChangeListener(this);
        this.startDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // Start from the current week's Monday

        setLayout(new BorderLayout());

        // Initialize loading spinner
        loadingSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
        loadingSpinner.setVisible(false); // Initially hidden

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("My Meal Plan", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font size for the title
        topPanel.add(titleLabel, BorderLayout.NORTH);

        weekLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size for the week range
        updateWeekLabel();
        topPanel.add(weekLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Add content panel for meal grid
        add(contentPanel, BorderLayout.CENTER);

        // Add bottom panel for navigation and action buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Navigation buttons with wide spacing and padding
        JPanel navigationPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(evt -> {
            groceryListController.switchToSearchView();
        });
//        JButton previousButton = new JButton("Previous");
//        JButton nextButton = new JButton("Next");

//        backButton.addActionListener(evt -> {
//            viewManagerModel.setState(Constants.SEARCH_VIEW);
//            viewManagerModel.firePropertyChanged();
//        });

//        previousButton.addActionListener(e -> navigateToWeek(-1));
//        nextButton.addActionListener(e -> navigateToWeek(1));

        // Create panels to wrap buttons and add padding
        JPanel previousPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        previousPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Left padding
        previousPanel.add(backButton);
//        previousPanel.add(previousButton);

        JPanel nextPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Right padding
//        nextPanel.add(nextButton);

        // Add to navigationPanel
        navigationPanel.add(previousPanel, BorderLayout.WEST);
        navigationPanel.add(nextPanel, BorderLayout.EAST);

        bottomPanel.add(navigationPanel, BorderLayout.NORTH);

        // Action buttons
        JPanel actionPanel = new JPanel();
        JButton generateGroceryListButton = new JButton("Generate Grocery List");
        JButton generateMealPlanButton = new JButton("Generate Meal Plan");

        generateGroceryListButton.addActionListener(e -> {
            groceryListController.execute();
        });
        generateMealPlanButton.addActionListener(e -> showGenerateMealPlanPopup());
        actionPanel.add(generateGroceryListButton);
        actionPanel.add(generateMealPlanButton);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize default view with empty day panels
        initializeEmptyPanels();

    }

    /**
     * Initializes the content panel with empty day panels.
     */
    private void initializeEmptyPanels() {
        contentPanel.removeAll();
        LocalDate current = startDate;
        for (int i = 0; i < 7; i++) {
            JPanel dayPanel = createDayPanel(current, new ArrayList<>());
            contentPanel.add(dayPanel);
            current = current.plusDays(1);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Updates the week label based on the current start date.
     */
    private void updateWeekLabel() {
        LocalDate endDate = startDate.plusDays(6);
        weekLabel.setText("Week of " + startDate + " to " + endDate);
    }

    /**
     * Navigates to a different week.
     *
     * @param weeksOffset The number of weeks to navigate. Negative for previous, positive for next.
     */
    private void navigateToWeek(int weeksOffset) {
        startDate = startDate.plusWeeks(weeksOffset);
        updateWeekLabel();
        initializeEmptyPanels();
        updateMealPlan(mealPlanData);
    }

    /**
     * Displays a pop-up for generating a meal plan using JSpinner for date selection.
     */
    private void showGenerateMealPlanPopup() {
        JDialog popup = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Generate Meal Plan", true);
        popup.setLayout(new BorderLayout());

        JLabel descriptionLabel = new JLabel("Plan Your Meal For a Week from:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        popup.add(descriptionLabel, BorderLayout.NORTH);

        // Create a date spinner
        JPanel centerPanel = new JPanel(new FlowLayout());
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        centerPanel.add(dateSpinner);
        popup.add(centerPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Generate");
        confirmButton.addActionListener(e -> {
            // Get the selected date
            Date selectedDate = (Date) dateSpinner.getValue();
            LocalDate startDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (controller != null) {
                controller.execute("None", startDate.toString()); // Default diet for now
            }
            popup.dispose();
        });
        popup.add(confirmButton, BorderLayout.SOUTH);

        popup.setSize(300, 200);
        popup.setLocationRelativeTo(this);
        popup.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof WeeklyMealState) {
            final WeeklyMealState state = (WeeklyMealState) evt.getNewValue();
            final Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlanData = state.getMealPlan();
            this.mealPlanData = mealPlanData;
            updateMealPlan(mealPlanData);
        }
        if (evt.getNewValue() instanceof UpdateMealsState) {
            final UpdateMealsState state = (UpdateMealsState) evt.getNewValue();
            final Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlanData = state.getMealPlan();
            this.mealPlanData = mealPlanData;
            updateMealPlan(mealPlanData);
        }
        // if page switched to this then we update the meal plan
//        if (evt.getNewValue() instanceof ViewManagerState) {
//            // we only care if it switched to this view
//            final ViewManagerState state = (ViewManagerState) evt.getNewValue();
//            if (!state.getViewName().equals(Constants.MEAL_PLAN_VIEW)) {
//                return;
//            }
//            // test that we are not in this page
//            // fetch meal plan data
//            final SessionManager sessionManager = SessionManager.getInstance();
//            final User user = sessionManager.getCurrentUser();
//            user.getMealIds();
//
//            updateMealsController.execute(user);
//
//        }
//        switch (evt.getPropertyName()) {
//            case "mealPlan":
//                final Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlanData = (Map<LocalDate, List<GenerateMealPlanRecipeDto>>) evt.getNewValue();
//                updateMealPlan(mealPlanData);
//                break;
//            case "isLoading":
//                showLoadingSpinner((boolean) evt.getNewValue());
//                break;
//            case "errorMessage":
//                String errorMessage = (String) evt.getNewValue();
//                if (errorMessage != null && !errorMessage.isEmpty()) {
//                    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                break;
//        }
    }

    private void updateMealPlan(Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlanData) {
        contentPanel.removeAll();
        for (Map.Entry<LocalDate, List<WeeklyMealRecipeDto>> entry : mealPlanData.entrySet()) {
            JPanel dayPanel = createDayPanel(entry.getKey(), entry.getValue());
            contentPanel.add(dayPanel);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDayPanel(LocalDate date, List<WeeklyMealRecipeDto> recipes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        String dayAbbreviation = date.getDayOfWeek().toString().substring(0, 3); // e.g., MON, TUE
        JLabel dateLabel = new JLabel(dayAbbreviation + " - " + date);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dateLabel);

        for (WeeklyMealRecipeDto recipe : recipes) {
            JPanel recipePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel recipeLabel = new JLabel(recipe.getTitle());
            JButton removeButton = new JButton("-");
            removeButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this recipe?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    recipes.remove(recipe);
                    updateMealPlan(viewModel.getMealPlan());
                }
            });
            recipePanel.add(recipeLabel);
            recipePanel.add(removeButton);
            panel.add(recipePanel);
        }

        return panel;
    }

    private void showLoadingSpinner(boolean show) {
        loadingSpinner.setVisible(show);
        contentPanel.setVisible(!show);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GenerateMealPlanViewModel viewModel = new GenerateMealPlanViewModel();
//            MealPlanView mealPlanView = new MealPlanView(viewModel);
//
//            JFrame frame = new JFrame("Meal Plan Viewer");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(1200, 800);
//            frame.add(mealPlanView);
//            frame.setVisible(true);
//        });
//    }


    public void setGroceryListController(GroceryListController groceryListController) {
        this.groceryListController = groceryListController;
    }

    public String getViewName() {
        return Constants.MEAL_PLAN_VIEW;
    }
}
