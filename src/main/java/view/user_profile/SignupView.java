package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.user_profile.signup.SignupController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class SignupView extends BaseView {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private final JLabel errorLabel;
    private final JButton signupButton;
    private final JButton cancelButton;

    private final SignupController signupController;

    public SignupView(SignupController signupController, ViewManagerModel viewManagerModel) {
        super("signup view", viewManagerModel);

        this.signupController = signupController;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        repeatPasswordField = new JPasswordField(20);
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);

        signupButton = new JButton("Signup");
        cancelButton = new JButton("Cancel");

        signupButton.addActionListener(evt -> createUser());
        cancelButton.addActionListener(evt -> navigateToWelcomeView());

        add(new JLabel("Signup"));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Repeat Password:"));
        add(repeatPasswordField);
        add(errorLabel);
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
        repeatPasswordField.setText("");
    }

    private void createUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());

        if (!password.equals(repeatPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        try {
            signupController.execute(username, password, repeatPassword);
            navigateToSetupView();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void navigateToWelcomeView() {
        viewManagerModel.setState(new ViewManagerState("welcome view", null));
        viewManagerModel.firePropertyChanged();
    }

    private void navigateToSetupView() {
        viewManagerModel.setState(new ViewManagerState("setup view", null));
        viewManagerModel.firePropertyChanged();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRepeatPassword() {
        return new String(repeatPasswordField.getPassword());
    }

    public void displayError(String message) {
        errorLabel.setText(message);
    }
}
