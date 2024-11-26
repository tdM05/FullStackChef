package view;

import javax.swing.*;
import java.awt.*;
import interface_adapter.UserProfileViewModel;

public class ProfileView extends JPanel {
    private final JLabel usernameLabel;
    private final JLabel displayNameLabel;
    private Runnable changePasswordListener;
    private Runnable changeDisplayNameListener;

    public ProfileView(UserProfileViewModel userProfileViewModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        displayNameLabel = new JLabel("Display Name: " + userProfileViewModel.getState().getDisplayName());

        JButton changePasswordButton = new JButton("Change Password");
        JButton changeDisplayNameButton = new JButton("Change Display Name");

        changePasswordButton.addActionListener(e -> {
            if (changePasswordListener != null) changePasswordListener.run();
        });

        changeDisplayNameButton.addActionListener(e -> {
            if (changeDisplayNameListener != null) changeDisplayNameListener.run();
        });

        add(usernameLabel);
        add(displayNameLabel);
        add(changePasswordButton);
        add(changeDisplayNameButton);
    }

    public void addChangePasswordListener(Runnable listener) {
        this.changePasswordListener = listener;
    }

    public void addChangeDisplayNameListener(Runnable listener) {
        this.changeDisplayNameListener = listener;
    }
}
