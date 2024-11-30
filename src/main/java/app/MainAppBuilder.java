package app;

import entity.user_profile.UserFactory;

import view.*;

import javax.swing.*;
import java.awt.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class MainAppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private UserFactory userFactory = new CommonUserFactory();
    private ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager;

    public MainAppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Favorite Use Case to the application.
     * @return this AppBuilder
     */

    /**
     * Builds the application.
     * @return the built application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Recipe App");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel, application);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.setSize(new Dimension(380, 250));
        application.setResizable(false);
        application.setLocationRelativeTo(null);

        return application;
    }
}
