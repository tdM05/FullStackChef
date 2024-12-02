package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.login.LoginController;
import interface_adapter.user_profile.login.LoginState;
import interface_adapter.user_profile.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final String viewName = "loginView";
    private final ViewManagerModel viewManagerModel;
    private LoginController loginController;
    private final LoginViewModel loginViewModel;

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = createErrorLabel();
    private final JButton loginButton = createButton("Login", this::authenticateUser);
    private final JButton signupButton = createButton("Go to Signup", this::navigateToSignupView);
    private final JButton cancelButton = createButton("Cancel", this::navigateToWelcomeView);

    public LoginView(LoginController loginController, LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.loginController = loginController;
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;

        this.loginViewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupComponents();
        addDocumentListeners();
    }

    private void setupComponents() {
        add(createTitleLabel("Login Screen"));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(errorLabel);
        add(loginButton);
        add(signupButton);
        add(cancelButton);
    }

    private JLabel createTitleLabel(String text) {
        JLabel titleLabel = new JLabel(text);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return titleLabel;
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

    private void addDocumentListeners() {
        addFieldListener(usernameField, this::updateUsername);
        addFieldListener(passwordField, this::updatePassword);
    }

    private void addFieldListener(JTextField field, Runnable updateState) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateState.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateState.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateState.run();
            }
        });
    }

    private void updateUsername() {
        LoginState state = loginViewModel.getState();
        state.setUsername(usernameField.getText());
        loginViewModel.setState(state);
    }

    private void updatePassword() {
        LoginState state = loginViewModel.getState();
        state.setPassword(new String(passwordField.getPassword()));
        loginViewModel.setState(state);
    }

    private void authenticateUser() {
        try {
            loginController.execute(
                    loginViewModel.getState().getUsername(),
                    loginViewModel.getState().getPassword()
            );
            viewManagerModel.setState("searchView");
        } catch (Exception e) {
            displayError(e.getMessage());
        }
    }

    private void navigateToSignupView() {
        viewManagerModel.setState("signupView");
    }

    private void navigateToWelcomeView() {
        viewManagerModel.setState("welcomeView");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            refresh();
        }
    }

    private void refresh() {
        LoginState state = loginViewModel.getState();
        usernameField.setText(state.getUsername());
        passwordField.setText(state.getPassword());
        errorLabel.setText(state.getLoginError());
    }

    public void displayError(String message) {
        errorLabel.setText(message);
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }
}
