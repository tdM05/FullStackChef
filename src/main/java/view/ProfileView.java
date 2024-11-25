package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interface_adapter.UserProfileViewModel;
import interface_adapter.change_password.ChangePasswordController;

public class ProfileView {
    private JLabel usernameLabel;
    private JLabel displayNameLabel;
    private JTextField displayNameField;
    private JPasswordField newPasswordField;
    private JButton changePasswordButton;
    private JButton changeDisplayNameButton;
    private JPanel panel;

    public ProfileView(UserProfileViewModel userProfileViewModel, ChangePasswordController changePasswordController) {
        JFrame frame = new JFrame("Profile View");
        panel = new JPanel(new GridLayout(6, 2));

        usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        displayNameLabel = new JLabel("Display Name: " + userProfileViewModel.getState().getDisplayName());
        displayNameField = new JTextField(userProfileViewModel.getState().getDisplayName());
        newPasswordField = new JPasswordField();
        changePasswordButton = new JButton("Change Password");
        changeDisplayNameButton = new JButton("Change Display Name");

        panel.add(usernameLabel);
        panel.add(new JLabel()); // Empty cell
        panel.add(displayNameLabel);
        panel.add(displayNameField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(changePasswordButton);
        panel.add(changeDisplayNameButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordController.execute(
                        userProfileViewModel.getState().getName(),
                        new String(newPasswordField.getPassword())
                );
            }
        });

        changeDisplayNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileViewModel.getState().setDisplayName(displayNameField.getText());
                userProfileViewModel.firePropertyChanged();
                displayNameLabel.setText("Display Name: " + userProfileViewModel.getState().getDisplayName());
            }
        });

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}