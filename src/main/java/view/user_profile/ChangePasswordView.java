package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.user_profile.change_password.ChangePasswordController;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ChangePasswordView extends BaseView {
    private final String viewName = "changePasswordView";
    private final UserProfileViewModel userProfileViewModel;
    private final ChangePasswordController controller;

    private final JLabel usernameLabel = new JLabel();
    private final JPasswordField newPasswordField = new JPasswordField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);

    public ChangePasswordView(UserProfileViewModel userProfileViewModel, ChangePasswordController controller, ViewManagerModel viewManagerModel) {
        super("changePasswordView", viewManagerModel);
        this.userProfileViewModel = userProfileViewModel;
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(usernameLabel);
        add(new JLabel("New Password:"));
        add(newPasswordField);
        add(new JLabel("Repeat New Password:"));
        add(repeatPasswordField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> handleSave());
        cancelButton.addActionListener(e -> navigateTo("profileView"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        usernameLabel.setText("Username: " + userProfileViewModel.getState().getName());
    }

    private void handleSave() {
        String newPassword = new String(newPasswordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());

        if (newPassword.isBlank() || repeatPassword.isBlank()) {
            JOptionPane.showMessageDialog(this, "Password fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            controller.execute(userProfileViewModel.getState().getName(), newPassword, repeatPassword);
            JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            navigateTo("profileView");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    public String getViewName() {
        return viewName;
    }
}
