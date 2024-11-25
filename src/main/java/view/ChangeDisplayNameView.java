package view;

import interface_adapter.UserProfileViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeDisplayNameView extends JPanel {
    private JLabel usernameLabel;
    private JTextField displayNameField;
    private JButton saveButton;
    private JButton cancelButton;

    public ChangeDisplayNameView(UserProfileViewModel userProfileViewModel) {
        usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        displayNameField = new JTextField(20);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileViewModel.getState().setDisplayName(displayNameField.getText());
                userProfileViewModel.firePropertyChanged();
                navigateToProfileView();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToProfileView();
            }
        });

        add(usernameLabel);
        add(new JLabel("New Display Name:"));
        add(displayNameField);
        add(saveButton);
        add(cancelButton);
    }

    private void navigateToProfileView() {
        // Navigate to ProfileView
    }
}