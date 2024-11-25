package view;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.UserProfileViewModel;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordView extends JPanel {
    private final JPasswordField newPasswordField = new JPasswordField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();
    private Runnable saveListener;
    private Runnable cancelListener;

    public ChangePasswordView(ChangePasswordController controller, UserProfileViewModel userProfileViewModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        errorLabel.setForeground(Color.RED);

        add(usernameLabel);
        add(new JLabel("New Password:"));
        add(newPasswordField);
        add(new JLabel("Repeat New Password:"));
        add(repeatPasswordField);
        add(errorLabel);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String repeatPassword = new String(repeatPasswordField.getPassword());
            controller.execute(userProfileViewModel.getState().getName(), newPassword, repeatPassword);
        });

        cancelButton.addActionListener(e -> {
            if (cancelListener != null) cancelListener.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    public void displayError(String message) {
        errorLabel.setText(message);
    }

    public void addSaveListener(Runnable listener) {
        this.saveListener = listener;
    }

    public void addCancelListener(Runnable listener) {
        this.cancelListener = listener;
    }
}
