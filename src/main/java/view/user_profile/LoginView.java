package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.user_profile.login.LoginController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class LoginView extends BaseView {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel errorLabel;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JButton cancelButton;

    private final LoginController loginController;

    public LoginView(LoginController loginController, ViewManagerModel viewManagerModel) {
        super("login view", viewManagerModel);

        this.loginController = loginController;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");
        cancelButton = new JButton("Cancel");

        loginButton.addActionListener(evt -> authenticateUser());
        signupButton.addActionListener(evt -> navigateToSignupView());
        cancelButton.addActionListener(evt -> navigateToWelcomeView());

        add(new JLabel("Login"));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(errorLabel);
        add(loginButton);
        add(signupButton);
        add(cancelButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        errorLabel.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            loginController.execute(username, password);
            navigateToSearchView();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void navigateToSignupView() {
        viewManagerModel.setState(new ViewManagerState("signup view", null));
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToWelcomeView() {
        viewManagerModel.setState(new ViewManagerState("welcome view", null));
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToSearchView() {
        viewManagerModel.setState(new ViewManagerState("search view", null));
        viewManagerModel.firePropertyChanged();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void displayError(String message) {
        errorLabel.setText(message);
    }
}
