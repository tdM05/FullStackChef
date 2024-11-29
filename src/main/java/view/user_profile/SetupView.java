package view.user_profile;

import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class SetupView extends BaseView {
    private final JTextField displayNameField;
    private final JButton skipButton;
    private final JButton nextButton;
    private final UserProfileViewModel userProfileViewModel;

    public SetupView(UserProfileViewModel userProfileViewModel, ViewManagerModel viewManagerModel) {
        super("setup view", viewManagerModel);

        this.userProfileViewModel = userProfileViewModel;

        displayNameField = new JTextField(20);
        skipButton = new JButton("Skip");
        nextButton = new JButton("Next");

        skipButton.addActionListener(evt -> navigateToSearchView());
        nextButton.addActionListener(evt -> updateDisplayNameAndNavigate());

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
        displayNameField.setText("");
    }

    private void updateDisplayNameAndNavigate() {
        userProfileViewModel.getState().setDisplayName(displayNameField.getText());
        userProfileViewModel.firePropertyChanged();
        navigateToSearchView();
    }

    private void navigateToSearchView() {
        viewManagerModel.setState(new ViewManagerState("search view", null));
        viewManagerModel.firePropertyChanged();
    }
}
