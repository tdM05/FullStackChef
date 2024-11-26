package view;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionListener;

public class WelcomeView extends JPanel {
    private Runnable guestListener;
    private Runnable loginSignupListener;

    public WelcomeView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton guestButton = new JButton("Continue as Guest");
        guestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guestButton.addActionListener(e -> {
            if (guestListener != null) guestListener.run();
        });

        JButton loginSignupButton = new JButton("Login/Signup");
        loginSignupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginSignupButton.addActionListener(e -> {
            if (loginSignupListener != null) loginSignupListener.run();
        });

        add(welcomeLabel);
        add(guestButton);
        add(loginSignupButton);
    }

    public void addGuestListener(Runnable listener) {
        this.guestListener = listener;
    }

    public void addLoginSignupListener(Runnable listener) {
        this.loginSignupListener = listener;
    }
}
