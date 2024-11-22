package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Custom Profile class
public class Profile extends JPanel {
    private Color circleColor = Color.GRAY;
    private JPopupMenu profileDropDown;
    private JMenuItem groceryListButton;

    public Profile() {
        setPreferredSize(new Dimension(55, 55));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Initialize the popup menu
        profileDropDown = new JPopupMenu();
        profileDropDown.setBackground(Color.DARK_GRAY);
        profileDropDown.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenuItem profileButton = new JMenuItem("Profile");
        JMenuItem favoriteButton = new JMenuItem("Favorite");
        JMenuItem mealPlanButton = new JMenuItem("Meal Plan");
        this.groceryListButton = new JMenuItem("Grocery List");
        // add listener to groceryListButton
        groceryListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Grocery List button clicked");

            }
        });
        JMenuItem dietButton = new JMenuItem("Diet");
        JMenuItem logoutButton = new JMenuItem("Logout");

        customizeButton(profileButton);
        customizeButton(favoriteButton);
        customizeButton(mealPlanButton);
        customizeButton(groceryListButton);
        customizeButton(dietButton);
        customizeButton(logoutButton);

        profileDropDown.add(profileButton);
        profileDropDown.add(favoriteButton);
        profileDropDown.add(mealPlanButton);
        profileDropDown.add(groceryListButton);
        profileDropDown.add(dietButton);
        profileDropDown.addSeparator();
        profileDropDown.add(logoutButton);

        // Add hover effect for the dropdown
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                circleColor = Color.DARK_GRAY;
                repaint();
                // Show the popup menu below the circle
                profileDropDown.show(Profile.this, -70, getHeight());
            }

            @Override
            public void mouseExited(MouseEvent e) {
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

        profileDropDown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
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

    public JMenuItem getGroceryListButton() {
        return groceryListButton;
    }

    private void customizeButton(JMenuItem item) {
        item.setBackground(Color.DARK_GRAY);
        item.setForeground(Color.WHITE);
        item.setFont(new Font("SansSerif", Font.BOLD, 12));
        item.setOpaque(true);
        item.setPreferredSize(new Dimension(120,40));
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
}