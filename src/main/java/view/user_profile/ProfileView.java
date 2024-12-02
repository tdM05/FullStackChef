package view.user_profile;

import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ProfileView extends BaseView {
    private final String viewName = "profileView";
    private final UserProfileViewModel userProfileViewModel;
    private final JLabel usernameLabel;
    private final JLabel displayNameLabel;
    private final JButton changePasswordButton;
    private final JButton changeDisplayNameButton;

    public ProfileView(UserProfileViewModel userProfileViewModel, ViewManagerModel viewManagerModel) {
        super("profile view", viewManagerModel);
        this.userProfileViewModel = userProfileViewModel;
        this.userProfileViewModel.addPropertyChangeListener(this);

        usernameLabel = new JLabel();
        displayNameLabel = new JLabel();
        changePasswordButton = new JButton("Change Password");
        changeDisplayNameButton = new JButton("Change Display Name");

        changePasswordButton.addActionListener(evt -> navigateToChangePasswordView());
        changeDisplayNameButton.addActionListener(evt -> navigateToChangeDisplayNameView());

        add(usernameLabel);
        add(displayNameLabel);
        add(changePasswordButton);
        add(changeDisplayNameButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        usernameLabel.setText("Username: " + userProfileViewModel.getState().getName());
        displayNameLabel.setText("Display Name: " + userProfileViewModel.getState().getDisplayName());
    }

    private void navigateToChangePasswordView() {
        viewManagerModel.setState("change password");
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToChangeDisplayNameView() {
        viewManagerModel.setState("change display name");
        viewManagerModel.firePropertyChanged();
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JButton getChangeDisplayNameButton() {
        return changeDisplayNameButton;
    }

    public void addChangePasswordListener(Runnable listener) {
        changePasswordButton.addActionListener(e -> listener.run());
    }

    public void addChangeDisplayNameListener(Runnable listener) {
        changeDisplayNameButton.addActionListener(e -> listener.run());
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
