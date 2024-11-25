package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomeView extends JPanel {
    public WelcomeView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JButton guestButton = new JButton("Continue as Guest");
        JButton loginSignupButton = new JButton("Login/Signup");

        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        loginSignupButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(guestButton);
        add(Box.createVerticalStrut(10));
        add(loginSignupButton);
        add(Box.createVerticalGlue());

        guestButton.addActionListener(e -> {
            if (guestListener != null) guestListener.run();
        });

        loginSignupButton.addActionListener(e -> {
            if (loginSignupListener != null) loginSignupListener.run();
        });
    }

    private Runnable guestListener;
    private Runnable loginSignupListener;

    public void addGuestListener(Runnable listener) {
        guestListener = listener;
    }

    public void addLoginSignupListener(Runnable listener) {
        loginSignupListener = listener;
    }
}