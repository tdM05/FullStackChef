package view;

import interface_adapter.UserProfileViewModel;
import interface_adapter.change_password.ChangePasswordController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordView extends JPanel {
    private JLabel usernameLabel;
    private JPasswordField newPasswordField;
    private JPasswordField repeatPasswordField;
    private JButton saveButton;
    private JButton cancelButton;

    public ChangePasswordView(ChangePasswordController changePasswordController, UserProfileViewModel userProfileViewModel) {
        usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        newPasswordField = new JPasswordField(20);
        repeatPasswordField = new JPasswordField(20);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String repeatPassword = new String(repeatPasswordField.getPassword());
                if (newPassword.equals(repeatPassword)) {
                    changePasswordController.execute(userProfileViewModel.getState().getName(), newPassword);
                    navigateToProfileView();
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToProfileView();
            }
        });

        add(usernameLabel);
        add(new JLabel("New Password:"));
        add(newPasswordField);
        add(new JLabel("Repeat New Password:"));
        add(repeatPasswordField);
        add(saveButton);
        add(cancelButton);
    }

    private void navigateToProfileView() {
        // Navigate to ProfileView
    }
}