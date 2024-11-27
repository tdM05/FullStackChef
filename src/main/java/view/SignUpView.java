package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.login.LoginController;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.text.View;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignUpView extends JPanel implements PropertyChangeListener {
    private static SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField repeatPasswordField;
    private final JButton registerButton;
    private final JButton backButton;

    private SignupController signupController;

    public SignUpView(SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel.addPropertyChangeListener(this);
        this.viewManagerModel.addPropertyChangeListener(this);

        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.repeatPasswordField = new JPasswordField();
        this.registerButton = new JButton("Register");
        this.backButton = new JButton("Back");

        this.registerButton.addActionListener(evt -> {
            final String username = usernameField.getText();
            final String password = new String(passwordField.getPassword());
            final String repeatPassword = new String(repeatPasswordField.getPassword());
            signupController.execute(username, password, repeatPassword);
        });

        this.backButton.addActionListener(evt -> {
            final ViewManagerState state = new ViewManagerState(Constants.LOGIN_VIEW, null);
            viewManagerModel.setState(state);
            viewManagerModel.firePropertyChanged();
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameField);
        this.add(passwordField);
        this.add(repeatPasswordField);
        this.add(registerButton);
        this.add(backButton);

    }

    /**
     * Adds the controller to the view.
     * @param newSignupController the controller to add
     */
    public void addController(SignupController newSignupController) {
        this.signupController = newSignupController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof SignupState) {
            final SignupState state = (SignupState) evt.getNewValue();
            if (state.isSuccess()) {
                // switch to main page since we logged in successfully
                final ViewManagerState viewManagerState = new ViewManagerState(Constants.SEARCH_VIEW, null);
                viewManagerModel.setState(viewManagerState);
                viewManagerModel.firePropertyChanged();
            }
            else {
                // show error message
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }
}
