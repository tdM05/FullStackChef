package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;

    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();

        this.loginButton = new JButton("Login");
        this.registerButton = new JButton("Register");
        loginButton.addActionListener(evt -> {
            System.out.println("Login button pressed");
            final String password = new String(passwordField.getPassword());
            loginController.execute(usernameField.getText(), password);
        });
        this.registerButton.addActionListener(evt -> {
            System.out.println("Register button pressed");
            // here we switch to register view.
            final ViewManagerState viewManagerState = new ViewManagerState(Constants.SIGNUP_VIEW, null);
            viewManagerModel.setState(viewManagerState);
            viewManagerModel.firePropertyChanged();
        });
        // set layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(registerButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof LoginState) {
            final LoginState state = (LoginState) evt.getNewValue();
            final boolean success = state.isSuccess();
            if (success) {
                // if we logged in, go to main page
                final ViewManagerState viewManagerState = new ViewManagerState(Constants.SEARCH_VIEW, null);
                viewManagerModel.setState(viewManagerState);
                viewManagerModel.firePropertyChanged();
            }
            else {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
        if (evt.getNewValue() instanceof SignupState) {
            final SignupState state = (SignupState) evt.getNewValue();
            // the view doesn't need to change if the signup was successful
            // The user should click the login button after signing up successfully
            if (!state.isSuccess()) {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }

    public void setLoginController(LoginController newLoginController) {
        this.loginController = newLoginController;
    }
}

