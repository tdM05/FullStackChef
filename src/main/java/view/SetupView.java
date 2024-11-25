package view;

import interface_adapter.UserProfileViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupView extends JPanel {
    private JTextField displayNameField;
    private JButton skipButton;
    private JButton nextButton;

    public SetupView(UserProfileViewModel userProfileViewModel) {
        displayNameField = new JTextField(20);
        skipButton = new JButton("Skip");
        nextButton = new JButton("Next");

        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToSearchRecipeView();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileViewModel.getState().setDisplayName(displayNameField.getText());
                userProfileViewModel.firePropertyChanged();
                navigateToSearchRecipeView();
            }
        });

        add(new JLabel("Display Name:"));
        add(displayNameField);
        add(skipButton);
        add(nextButton);
    }

    private void navigateToSearchRecipeView() {
        // Navigate to SearchRecipeView
    }
}