package view;

import app.SessionUser;
import interface_adapter.display_favorites.DisplayFavoriteController;
import interface_adapter.display_history.DisplayHistoryController;
import interface_adapter.grocery_list.GroceryListController;
import interface_adapter.logout.LogoutController;
import interface_adapter.mealplan.update_meals.UpdateMealsController;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

// Custom Profile class
public class Profile extends JPanel {
    private final JMenuItem mealPlanButton;
    private Color circleColor = Color.GRAY;
    private JPopupMenu profileDropDown;
    private JMenuItem favoriteButton;
    private JMenuItem groceryListButton;
    private JMenuItem historyButton;
    private JMenuItem logoutButton;


    private DisplayFavoriteController displayFavoriteController;
    private DisplayHistoryController displayHistoryController;

    // Existing dietary restrictions
    private List<String> existingDietaryRestrictions = new ArrayList<>();
    // controllers
    GroceryListController groceryListController;
    private UpdateMealsController updateMealsController;
    private LogoutController logoutController;

    public Profile() {
        setPreferredSize(new Dimension(55, 55));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Initialize the popup menu
        profileDropDown = new JPopupMenu();
        profileDropDown.setBackground(Color.DARK_GRAY);
        profileDropDown.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenuItem profileButton = new JMenuItem("Profile");

        this.favoriteButton = new JMenuItem("Favorite");

        mealPlanButton = new JMenuItem("Meal Plan");
        mealPlanButton.addActionListener(e -> {
            SessionUser sessionUser = SessionUser.getInstance();
            updateMealsController.execute(sessionUser.getUser());
        });

        this.groceryListButton = new JMenuItem("Grocery List");
        groceryListButton.addActionListener(e -> {
            groceryListController.execute();
        });

        this.historyButton = new JMenuItem("History");

        this.logoutButton = new JMenuItem("Logout");

        customizeButton(profileButton);
        customizeButton(favoriteButton);
        customizeButton(mealPlanButton);
        customizeButton(groceryListButton);
        customizeButton(historyButton);
        customizeButton(logoutButton);

        profileDropDown.add(profileButton);
        profileDropDown.add(favoriteButton);
        profileDropDown.add(mealPlanButton);
        profileDropDown.add(groceryListButton);
        profileDropDown.add(historyButton);
        profileDropDown.addSeparator();
        profileDropDown.add(logoutButton);

        favoriteButton.addActionListener(e -> {
            displayFavoriteController.execute();
        });

        historyButton.addActionListener(e -> {
            displayHistoryController.execute();
        });

        // Add hover effect for the dropdown
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                circleColor = Color.DARK_GRAY;
                repaint();
                // Show the popup menu below the circle
                profileDropDown.show(Profile.this, -70, getHeight());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Check if mouse exits both the Profile and dropdown
                Point mouseLocation = e.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(mouseLocation, profileDropDown);
                if (!profileDropDown.getBounds().contains(mouseLocation)) {
                    circleColor = Color.GRAY;
                    profileDropDown.setVisible(false);
                    repaint();
                }
            }
        });

        profileDropDown.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Check if mouse is still near the menu bounds
                Point mouseLocation = e.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(mouseLocation, profileDropDown);

                Rectangle extendedBounds = new Rectangle(-10, -10,
                        profileDropDown.getWidth() + 20,
                        profileDropDown.getHeight() + 20);

                if (!extendedBounds.contains(mouseLocation)) {
                    profileDropDown.setVisible(false);
                }
            }
        });
    }

    /**
     * Customizes the appearance of a JMenuItem.
     *
     * @param item the JMenuItem to customize
     */
    private void customizeButton(JMenuItem item) {
        item.setBackground(Color.DARK_GRAY);
        item.setForeground(Color.WHITE);
        item.setFont(new Font("SansSerif", Font.BOLD, 12));
        item.setOpaque(true);
        item.setPreferredSize(new Dimension(120, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Determine size and position
        int diameter = Math.min(getWidth(), getHeight()) - 1;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        // Draw the circle
        g2d.setColor(circleColor);
        g2d.fillOval(x, y, diameter, diameter);

        // Optionally, draw initials or an icon in the circle
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, diameter / 2));
        FontMetrics fm = g2d.getFontMetrics();

        String initials = "P";
        int stringWidth = fm.stringWidth(initials);
        int stringHeight = fm.getAscent();
        int textX = x + (diameter - stringWidth) / 2;
        int textY = y + (diameter + stringHeight) / 2 - 4;
        g2d.drawString(initials, textX, textY);

        g2d.dispose();
    }
    public void setGroceryListController(GroceryListController groceryListController) {
        this.groceryListController = groceryListController;
    }

    public void setUpdateMealsController(UpdateMealsController updateMealsController) {
        this.updateMealsController = updateMealsController;
    }

    public void setDisplayFavoriteController(DisplayFavoriteController displayFavoriteController) {
        this.displayFavoriteController = displayFavoriteController;
    }

    public void setDisplayHistoryController(DisplayHistoryController displayHistoryController) {
        this.displayHistoryController = displayHistoryController;
    }
}
