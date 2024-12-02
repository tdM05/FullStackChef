package view.user_profile;

import interface_adapter.user_profile.UserProfileViewModel;

import javax.swing.*;

public class ChangeDisplayNameView extends JPanel {
    private final String viewName = "changeDisplayNameView";
    private final JTextField displayNameField = new JTextField(20);
    private Runnable saveListener;
    private Runnable cancelListener;

    public ChangeDisplayNameView(UserProfileViewModel userProfileViewModel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: " + userProfileViewModel.getState().getName());
        add(usernameLabel);

        add(new JLabel("New Display Name:"));
        add(displayNameField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            userProfileViewModel.getState().setDisplayName(displayNameField.getText());
            userProfileViewModel.firePropertyChanged();
            if (saveListener != null) saveListener.run();
        });

        cancelButton.addActionListener(e -> {
            if (cancelListener != null) cancelListener.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    public void addSaveListener(Runnable listener) {
        this.saveListener = listener;
    }

    public void addCancelListener(Runnable listener) {
        this.cancelListener = listener;
    }
}
