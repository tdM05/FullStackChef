package view;

import interface_adapter.UserProfileViewModel;

import javax.swing.*;
import java.awt.*;

public class SetupView extends JPanel {
    private final JTextField displayNameField = new JTextField(20);
    private Runnable skipListener;
    private Runnable nextListener;

    public SetupView(UserProfileViewModel userProfileViewModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Display Name:"));
        add(displayNameField);

        JButton skipButton = new JButton("Skip");
        JButton nextButton = new JButton("Next");

        skipButton.addActionListener(e -> {
            if (skipListener != null) skipListener.run();
        });

        nextButton.addActionListener(e -> {
            userProfileViewModel.getState().setDisplayName(displayNameField.getText());
            userProfileViewModel.firePropertyChanged();
            if (nextListener != null) nextListener.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(skipButton);
        buttonPanel.add(nextButton);
        add(buttonPanel);
    }

    public void addSkipListener(Runnable listener) {
        this.skipListener = listener;
    }

    public void addNextListener(Runnable listener) {
        this.nextListener = listener;
    }
}
