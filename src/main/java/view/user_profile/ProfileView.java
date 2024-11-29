package view.user_profile;

import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ProfileView extends BaseView {
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
        viewManagerModel.setState(new ViewManagerState("change password", null));
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToChangeDisplayNameView() {
        viewManagerModel.setState(new ViewManagerState("change display name", null));
        viewManagerModel.firePropertyChanged();
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JButton getChangeDisplayNameButton() {
        return changeDisplayNameButton;
    }
}
