package view;

import javax.swing.*;

public class WelcomeView {
    private JButton guestButton;
    private JButton loginORSignupButton;

    public WelcomeView() {
        guestButton = new JButton("Continue as Guest");
        loginORSignupButton = new JButton("Login/Signup");

        guestButton.addActionListener(e -> navigateToSearchRecipeView());
        loginORSignupButton.addActionListener(e -> navigateToLoginView());
    }

    private void navigateToSearchRecipeView() {
        // Navigate to SearchRecipeView
    }

    private void navigateToLoginView() {
        // Navigate to SignupView
    }
}