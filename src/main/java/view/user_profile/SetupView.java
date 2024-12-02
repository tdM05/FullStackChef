package view.user_profile;

import interface_adapter.user_profile.setup.SetupController;
import interface_adapter.user_profile.UserProfileViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class SetupView extends BaseView {
    private final JTextField displayNameField;
    private final JButton skipButton;
    private final JButton nextButton;
    private final UserProfileViewModel userProfileViewModel;

    public SetupView(SetupController setupController, UserProfileViewModel userProfileViewModel) {
        super("setupView", setupController.getViewManagerModel());
        this.userProfileViewModel = userProfileViewModel;

        displayNameField = new JTextField(20);
        skipButton = new JButton("Skip");
        nextButton = new JButton("Next");

        // Button actions
        skipButton.addActionListener(evt -> setupController.skipSetup());
        nextButton.addActionListener(evt -> setupController.updateDisplayNameAndNavigate(displayNameField.getText()));

        setupLayout();
    }

    private void setupLayout() {
        add(new JLabel("Setup Display Name"));
        add(displayNameField);
        add(skipButton);
        add(nextButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        // Retrieve the current display name or use the default username
        String currentDisplayName = userProfileViewModel.getState().getDisplayName();
        displayNameField.setText(currentDisplayName != null ? currentDisplayName : "");
    }
}
