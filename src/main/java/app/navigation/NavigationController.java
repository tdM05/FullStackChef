package app.navigation;

import javax.swing.*;

/**
 * This is used to navigate between views in the application.
 */
final class NavigationController {
    private static NavigationController instance;
    private JFrame mainFrame;

    private NavigationController(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Get an instance of the NavigationController, while ensuring no duplicate instances are created.
     * @param mainFrame the main frame of the application
     */
    public static NavigationController getInstance(JFrame mainFrame) {
        if (instance == null) {
            instance = new NavigationController(new JFrame("Full Stack Chef"));
            instance.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            instance.mainFrame.setSize(400, 300);
            instance.mainFrame.setVisible(true);
        }
        return instance;
    }

    /**
     * Navigate to the given panel
     * @param panel
     */
    public void navigateTo(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
