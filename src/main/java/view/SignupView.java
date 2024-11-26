//package view;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class SignupView extends JPanel {
//    private final JTextField usernameField = new JTextField(20);
//    private final JPasswordField passwordField = new JPasswordField(20);
//    private final JPasswordField repeatPasswordField = new JPasswordField(20);
//    private final JLabel errorLabel = new JLabel();
//
//    private Runnable signupListener;
//    private Runnable cancelListener;
//
//    public SignupView() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        JLabel titleLabel = new JLabel("Signup");
//        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
//
//        add(titleLabel);
//        add(new JLabel("Username:"));
//        add(usernameField);
//        add(new JLabel("Password:"));
//        add(passwordField);
//        add(new JLabel("Repeat Password:"));
//        add(repeatPasswordField);
//
//        JButton signupButton = new JButton("Signup");
//        signupButton.addActionListener(e -> {
//            if (signupListener != null) signupListener.run();
//        });
//
//        JButton cancelButton = new JButton("Cancel");
//        cancelButton.addActionListener(e -> {
//            if (cancelListener != null) cancelListener.run();
//        });
//
//        errorLabel.setForeground(Color.RED);
//        add(errorLabel);
//        add(signupButton);
//        add(cancelButton);
//    }
//
//    public String getUsername() {
//        return usernameField.getText();
//    }
//
//    public String getPassword() {
//        return new String(passwordField.getPassword());
//    }
//
//    public String getRepeatPassword() {
//        return new String(repeatPasswordField.getPassword());
//    }
//
//    public void displayError(String message) {
//        errorLabel.setText(message);
//    }
//
//    public void addSignupListener(Runnable listener) {
//        this.signupListener = listener;
//    }
//
//    public void addCancelListener(Runnable listener) {
//        this.cancelListener = listener;
//    }
//}
