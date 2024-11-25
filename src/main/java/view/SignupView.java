package view;

import javax.swing.*;
import java.awt.*;

public class SignupView extends JPanel {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();

    public SignupView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Signup");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        errorLabel.setForeground(Color.RED);

        add(titleLabel);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Repeat Password:"));
        add(repeatPasswordField);
        add(errorLabel);

        JButton signupButton = new JButton("Signup");
        JButton loginInsteadButton = new JButton("Login Instead");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(signupButton);
        buttonPanel.add(loginInsteadButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel);

        signupButton.addActionListener(e -> {
            if (signupListener != null) signupListener.run();
        });

        loginInsteadButton.addActionListener(e -> {
            if (loginInsteadListener != null) loginInsteadListener.run();
        });

        cancelButton.addActionListener(e -> {
            if (cancelListener != null) cancelListener.run();
        });
    }

    private Runnable signupListener;
    private Runnable loginInsteadListener;
    private Runnable cancelListener;

    public void addSignupListener(Runnable listener) {
        signupListener = listener;
    }

    public void addLoginInsteadListener(Runnable listener) {
        loginInsteadListener = listener;
    }

    public void addCancelListener(Runnable listener) {
        cancelListener = listener;
    }
}
