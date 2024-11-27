package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.favorite.FavoriteController;
import interface_adapter.favorite.FavoriteViewModel;

import javax.swing.*;
import java.awt.*;

public class FavoriteView extends JPanel {

    private FavoriteController controller;

    public FavoriteView(FavoriteViewModel viewModel, ViewManagerModel viewManagerModel) {
        // Set layout for the panel
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Favorite", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setBackground(Color.black);
        add(titleLabel, BorderLayout.NORTH);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align the button to the left
        JButton backButton = new JButton("Back");
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            // Switch to the main page
            ViewManagerState state = new ViewManagerState(Constants.SEARCH_VIEW, null);
            viewManagerModel.setState(state);
            viewManagerModel.firePropertyChanged();
        });

        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Recipes", new JPanel()); // Placeholder for recipes content
        tabbedPane.addTab("Collections", new JPanel()); // Placeholder for collections content
        add(tabbedPane, BorderLayout.CENTER);
    }

    public void setFavoriteController(FavoriteController controller) {
        this.controller = controller;
    }
}