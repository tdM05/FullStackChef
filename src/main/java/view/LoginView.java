package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginView extends JPanel {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();

    private Runnable signupListener;
    private Runnable cancelListener;
    private Consumer<Void> loginListener;

    public LoginView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> {
            if (loginListener != null) loginListener.accept(null);
        });

        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(e -> {
            if (signupListener != null) signupListener.run();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            if (cancelListener != null) cancelListener.run();
        });

        errorLabel.setForeground(Color.RED);
        add(errorLabel);
        add(loginButton);
        add(signupButton);
        add(cancelButton);
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

    public void addLoginListener(Consumer<Void> listener) {
        this.loginListener = listener;
    }

    public void addSignupListener(Runnable listener) {
        this.signupListener = listener;
    }

    public void addCancelListener(Runnable listener) {
        this.cancelListener = listener;
    }
}
