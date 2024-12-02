package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ProfileView extends BaseView {
    private final String viewName = "profileView";
    private final UserProfileViewModel userProfileViewModel;

    private final JLabel usernameLabel = new JLabel();
    private final JLabel displayNameLabel = new JLabel();
    private final JButton changePasswordButton = new JButton("Change Password");
    private final JButton changeDisplayNameButton = new JButton("Change Display Name");
    private final JButton backButton = new JButton("Back");

    public ProfileView(UserProfileViewModel userProfileViewModel, ViewManagerModel viewManagerModel) {
        super("profileView", viewManagerModel);
        this.userProfileViewModel = userProfileViewModel;
        this.userProfileViewModel.addPropertyChangeListener(this);

        // Setup UI
        setupUI();

        // Button actions
        changePasswordButton.addActionListener(e -> navigateToChangePasswordView());
        changeDisplayNameButton.addActionListener(e -> navigateToChangeDisplayNameView());
        backButton.addActionListener(e -> navigateToSearchView());
    }

    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Profile and Settings"));
        add(usernameLabel);
        add(displayNameLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(changeDisplayNameButton);
        add(buttonPanel);

        refresh();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        // Update the labels with the current state from UserProfileViewModel
        usernameLabel.setText("Username: " + userProfileViewModel.getState().getName());
        displayNameLabel.setText("Display Name: " + userProfileViewModel.getState().getDisplayName());
    }

    private void navigateToChangePasswordView() {
        viewManagerModel.setState("changePasswordView");
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToChangeDisplayNameView() {
        viewManagerModel.setState("changeDisplayNameView");
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToSearchView() {
        viewManagerModel.setState("searchView");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
