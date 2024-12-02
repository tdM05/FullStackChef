package view.user_profile;

import app.SessionUser;
import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.user_profile.change_password.ChangePasswordController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ChangePasswordView extends BaseView {
    private final String viewName = "changePasswordView";
    private final UserProfileViewModel userProfileViewModel;
    private final ChangePasswordController controller;
    private final JLabel usernameLabel;
    private final JPasswordField newPasswordField;
    private final JPasswordField repeatPasswordField;
    private final JButton saveButton;
    private final JButton cancelButton;

    public ChangePasswordView(UserProfileViewModel userProfileViewModel, ChangePasswordController controller, ViewManagerModel viewManagerModel) {
        super("changePasswordView", viewManagerModel);
        this.userProfileViewModel = userProfileViewModel;
        this.controller = controller;

        usernameLabel = new JLabel();
        newPasswordField = new JPasswordField(20);
        repeatPasswordField = new JPasswordField(20);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(evt -> updatePassword());
        cancelButton.addActionListener(evt -> navigateToProfileView());

        add(usernameLabel);
        add(new JLabel("New Password:"));
        add(newPasswordField);
        add(new JLabel("Repeat New Password:"));
        add(repeatPasswordField);
        add(saveButton);
        add(cancelButton);
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

    private void updatePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if (!newPassword.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        controller.execute(userProfileViewModel.getState().getName(), newPassword, repeatPassword);
        navigateToProfileView();
    }

    private void navigateToProfileView() {
        viewManagerModel.setState("profile view");
        viewManagerModel.firePropertyChanged();
    }

    public void addSaveListener(Runnable listener) {
        saveButton.addActionListener(e -> listener.run());
    }

    public void addCancelListener(Runnable listener) {
        cancelButton.addActionListener(e -> listener.run());
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
