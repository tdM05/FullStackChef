package view.user_profile;

import interface_adapter.ViewManagerModel;

import javax.swing.*;

public class WelcomeView extends BaseView {
    private final String viewName = "welcomeView";
    private final JButton guestButton;
    private final JButton loginSignupButton;

    public WelcomeView(ViewManagerModel viewManagerModel) {
        super("welcomeView", viewManagerModel);

        guestButton = new JButton("Continue as Guest");
        loginSignupButton = new JButton("Login/Signup");

        // Notify ViewManagerModel to switch states
        guestButton.addActionListener(e -> viewManagerModel.setState("searchView"));
        loginSignupButton.addActionListener(e -> viewManagerModel.setState("loginView"));


        add(new JLabel("Welcome!"));
        add(guestButton);
        add(loginSignupButton);
    }

    @Override
    protected void refresh() {
        // No dynamic data to refresh
    }

    private void navigateAsGuest() {
        viewManagerModel.setState("searchView");
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToLoginSignupView() {
        viewManagerModel.setState("loginView");
        viewManagerModel.firePropertyChanged();
    }

    public JButton getGuestButton() {
        return guestButton;
    }

    public JButton getLoginSignupButton() {
        return loginSignupButton;
    }

    public void addGuestListener(Runnable listener) {
        guestButton.addActionListener(e -> listener.run());
    }

    public void addLoginSignupListener(Runnable listener) {
        loginSignupButton.addActionListener(e -> listener.run());
    }
}
