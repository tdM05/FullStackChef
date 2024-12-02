package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.logout.LogoutController;
import interface_adapter.user_profile.profile.ProfileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Profile extends JPanel {
    private Color circleColor = Color.GRAY;
    private JPopupMenu profileDropDown;
    private JMenuItem profileButton;
    private JMenuItem logoutButton;
    private JMenuItem favoriteButton;
    private JMenuItem groceryListButton;
    private JMenuItem dietButton;
    private ViewManagerModel viewManagerModel;

    private ProfileController profileController;
    private LogoutController logoutController;

    public Profile(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        setPreferredSize(new Dimension(55, 55));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Initialize the popup menu
        profileDropDown = new JPopupMenu();
        profileDropDown.setBackground(Color.DARK_GRAY);
        profileDropDown.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.profileButton = new JMenuItem("Profile");
        profileButton.addActionListener(e -> {
            profileController.execute();
        });

        this.logoutButton = new JMenuItem("Logout");
        this.logoutButton.addActionListener(e -> {
            logoutController.execute();
        });


        this.dietButton = new JMenuItem("Diet");

        // Customize buttons
        customizeButton(profileButton);
        customizeButton(dietButton);
        customizeButton(logoutButton);

        // Add buttons to the dropdown
        profileDropDown.add(profileButton);
        profileDropDown.add(dietButton);
        profileDropDown.addSeparator();
        profileDropDown.add(logoutButton);

        // Add ActionListener for Diet Button (Preserving existing functionality)
        addDietActionListener(dietButton);

        this.
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

    /**
     * Adds ActionListener for the Diet button (preserving dietary functionality).
     */
    private void addDietActionListener(JMenuItem dietButton) {
        dietButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDietaryRestrictionsDialog();
            }
        });
    }

    /**
     * Displays the Dietary Restrictions selection dialog.
     */
    private void showDietaryRestrictionsDialog() {
        // Define available diets
        String[] availableDiets = {
                "Gluten Free",
                "Ketogenic",
                "Vegetarian",
                "Lacto-Vegetarian",
                "Ovo-Vegetarian",
                "Vegan",
                "Pescetarian",
                "Paleo",
                "Primal",
                "Whole30"
        };

        // Create checkboxes for each diet
        JCheckBox[] checkBoxes = new JCheckBox[availableDiets.length];
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < availableDiets.length; i++) {
            checkBoxes[i] = new JCheckBox(availableDiets[i]);
            panel.add(checkBoxes[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Select Your Dietary Restrictions",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            List<String> selectedDiets = new ArrayList<>();
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    selectedDiets.add(checkBox.getText());
                }
            }
            // Invoke the use case to set dietary restrictions
            // dietaryController.setDietaryRestrictions(selectedDiets); // Use your dietary controller here
            System.out.println("Selected dietary restrictions: " + selectedDiets); // Debug log
        }
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

        String initials = "P";  // This could be dynamically updated based on user data
        int stringWidth = fm.stringWidth(initials);
        int stringHeight = fm.getAscent();
        int textX = x + (diameter - stringWidth) / 2;
        int textY = y + (diameter + stringHeight) / 2 - 4;
        g2d.drawString(initials, textX, textY);

        g2d.dispose();
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
