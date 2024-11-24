package view;

import interface_adapter.login.LoginController;

import javax.swing.*;

public class LoginView {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupInsteadButton;
    private JButton cancelButton;

    public LoginView(LoginController loginController) {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signupInsteadButton = new JButton("Signup Instead");
        cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> loginController.execute(
                usernameField.getText(),
                new String(passwordField.getPassword())
        ));
        signupInsteadButton.addActionListener(e -> navigateToSignupView());
        cancelButton.addActionListener(e -> navigateToWelcomeView());
    }

    private void navigateToSignupView() {
        // Navigate to SignupView
    }

    private void navigateToWelcomeView() {
        // Navigate to WelcomeView
    }
}
