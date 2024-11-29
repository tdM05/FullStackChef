package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;

import javax.swing.*;

public class WelcomeView extends BaseView {
    private final JButton guestButton;
    private final JButton loginSignupButton;

    public WelcomeView(ViewManagerModel viewManagerModel) {
        super("welcome view", viewManagerModel);

        guestButton = new JButton("Continue as Guest");
        loginSignupButton = new JButton("Login/Signup");

        guestButton.addActionListener(evt -> navigateAsGuest());
        loginSignupButton.addActionListener(evt -> navigateToLoginSignupView());

        add(new JLabel("Welcome!"));
        add(guestButton);
        add(loginSignupButton);
    }

    @Override
    protected void refresh() {
        // No dynamic data to refresh
    }

    private void navigateAsGuest() {
        viewManagerModel.setState(new ViewManagerState("search view", null));
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToLoginSignupView() {
        viewManagerModel.setState(new ViewManagerState("login view", null));
        viewManagerModel.firePropertyChanged();
    }

    public JButton getGuestButton() {
        return guestButton;
    }

    public JButton getLoginSignupButton() {
        return loginSignupButton;
    }
}
