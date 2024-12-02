package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View Manager for the program. It listens for property change events
 * in the ViewManagerModel and updates which View should be visible.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;
    private final JFrame applicationFrame;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel, JFrame applicationFrame) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);  // Listen to property changes
        this.applicationFrame = applicationFrame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            System.out.println("Changing view to: " + viewModelName); // Debug log

            cardLayout.show(views, viewModelName);

            // Get the currently visible component (the new view)
            Component currentView = null;
            for (Component comp : views.getComponents()) {
                if (comp.isVisible()) {
                    currentView = comp;
                    break;
                }
            }

            if (currentView != null) {
                Dimension preferredSize = currentView.getPreferredSize();

                // Adjust the frame size to match the preferred size of the current view
                applicationFrame.setSize(preferredSize.width, preferredSize.height);

                // Center the frame on the screen
                applicationFrame.setLocationRelativeTo(null);
            }
        }
    }
}
