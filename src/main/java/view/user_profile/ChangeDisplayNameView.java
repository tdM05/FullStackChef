package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;

import javax.swing.*;

public class ChangeDisplayNameView extends JPanel {
    private final String viewName = "changeDisplayNameView";
    private final JTextField displayNameField = new JTextField(20);
    private final ViewManagerModel viewManagerModel;
    private final UserProfileViewModel userProfileViewModel;

    public ChangeDisplayNameView(UserProfileViewModel userProfileViewModel, ViewManagerModel viewManagerModel) {
        this.userProfileViewModel = userProfileViewModel;
        this.viewManagerModel = viewManagerModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        add(usernameLabel);

        add(new JLabel("New Display Name:"));
        add(displayNameField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> handleSave());
        cancelButton.addActionListener(e -> navigateTo("profileView"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    private void handleSave() {
        // Update display name in the user profile state
        String newDisplayName = displayNameField.getText();
        if (newDisplayName.isBlank()) {
            JOptionPane.showMessageDialog(this, "Display name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userProfileViewModel.getState().setDisplayName(newDisplayName);
        userProfileViewModel.firePropertyChanged();

        // Navigate back to ProfileView
        navigateTo("profileView");
    }

    private void navigateTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    public String getViewName() {
        return viewName;
    }
}
