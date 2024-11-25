package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();

    public LoginView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        errorLabel.setForeground(Color.RED);

        add(titleLabel);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(errorLabel);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Signup");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel);

        loginButton.addActionListener(e -> {
            if (loginListener != null) loginListener.run();
        });

        signupButton.addActionListener(e -> {
            if (signupListener != null) signupListener.run();
        });

        cancelButton.addActionListener(e -> {
            if (cancelListener != null) cancelListener.run();
        });
    }

    private Runnable loginListener;
    private Runnable signupListener;
    private Runnable cancelListener;

    public void addLoginListener(Runnable listener) {
        loginListener = listener;
    }

    public void addSignupListener(Runnable listener) {
        signupListener = listener;
    }

    public void addCancelListener(Runnable listener) {
        cancelListener = listener;
    }
}
