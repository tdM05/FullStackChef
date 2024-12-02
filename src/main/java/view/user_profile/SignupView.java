package view.user_profile;

import interface_adapter.user_profile.signup.SignupController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

import static interface_adapter.user_profile.signup.SignupViewModel.*;

public class SignupView extends BaseView {
    private final String viewName = "signupView";
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private final JLabel errorLabel;
    private final JButton signupButton;
    private final JButton toLoginButton;
    private final JButton cancelButton;

    private SignupController signupController;

    public SignupView(SignupController signupController, ViewManagerModel viewManagerModel) {
        super("signupView", viewManagerModel);
        this.signupController = signupController;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        repeatPasswordField = new JPasswordField(20);
        errorLabel = createErrorLabel();

        signupButton = createButton(SIGNUP_BUTTON_LABEL, this::handleSignup);
        toLoginButton = createButton(TO_LOGIN_BUTTON_LABEL, this::navigateToLoginView);
        cancelButton = createButton(CANCEL_BUTTON_LABEL, this::navigateToWelcomeView);

        setupLayout();
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(Color.RED);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(evt -> action.run());
        return button;
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel(TITLE_LABEL));
        add(new JLabel(USERNAME_LABEL));
        add(usernameField);
        add(new JLabel(PASSWORD_LABEL));
        add(passwordField);
        add(new JLabel(REPEAT_PASSWORD_LABEL));
        add(repeatPasswordField);
        add(errorLabel);
        add(signupButton);
        add(toLoginButton);
        add(cancelButton);
    }

    private void handleSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());

        if (!password.equals(repeatPassword)) {
            displayError("Passwords do not match.");
            return;
        }

        try {
            signupController.execute(username, password, repeatPassword);

            // Provide success feedback (optional popup)
            JOptionPane.showMessageDialog(this,
                    "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    private void navigateToLoginView() {
        viewManagerModel.setState("loginView");
    }

    private void navigateToWelcomeView() {
        viewManagerModel.setState("welcomeView");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        refresh();
    }

    @Override
    protected void refresh() {
        usernameField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        errorLabel.setText("");
    }

    public void displayError(String message) {
        errorLabel.setText(message);
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

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
