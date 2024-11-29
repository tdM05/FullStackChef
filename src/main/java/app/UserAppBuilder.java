package app;

import data_access.user_profile.InMemoryGuestUserDataAccessObject;
import data_access.user_profile.UserAuthDataAccessObject;
import entity.user_profile.GuestUser;
import entity.user_profile.UserProfile;
import entity.user_profile.UserProfileFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.user_profile.change_password.ChangePasswordController;
import interface_adapter.user_profile.login.LoginController;
import interface_adapter.user_profile.signup.SignupController;
import view.SearchRecipeView;
import view.user_profile.*;

import javax.swing.*;

/**
 * Builder for the User Application.
 */
public class UserAppBuilder {
    private final UserProfileFactory userProfileFactory;
    private final UserAuthDataAccessObject regularUserDAO;
    private final InMemoryGuestUserDataAccessObject guestUserDAO;
    private final UserProfileViewModel userProfileViewModel;
    private final SearchRecipeView searchRecipeView;
    private final ViewManagerModel viewManagerModel;

    private ChangePasswordController changePasswordController;
    private LoginController loginController;
    private SignupController signupController;
    private JFrame frame;

    public UserAppBuilder(UserProfileFactory userProfileFactory, SearchRecipeView searchRecipeView, ViewManagerModel viewManagerModel) {
        this.userProfileFactory = userProfileFactory;
        this.regularUserDAO = new UserAuthDataAccessObject(userProfileFactory);
        this.guestUserDAO = new InMemoryGuestUserDataAccessObject();
        this.userProfileViewModel = new UserProfileViewModel();
        this.searchRecipeView = searchRecipeView;
        this.viewManagerModel = viewManagerModel;
    }

    public UserAppBuilder setChangePasswordController(ChangePasswordController controller) {
        this.changePasswordController = controller;
        return this;
    }

    public UserAppBuilder setLoginController(LoginController controller) {
        this.loginController = controller;
        return this;
    }

    public UserAppBuilder setSignupController(SignupController controller) {
        this.signupController = controller;
        return this;
    }

    public JFrame build() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize views
        WelcomeView welcomeView = createWelcomeView();
        LoginView loginView = createLoginView();
        SignupView signupView = createSignupView();
        SetupView setupView = createSetupView();
        ProfileView profileView = createProfileView();

        // Start with WelcomeView
        switchToView(welcomeView);

        return frame;
    }

    private WelcomeView createWelcomeView() {
        WelcomeView welcomeView = new WelcomeView(viewManagerModel);

        // Add action listeners directly to the buttons in WelcomeView
        JButton guestButton = welcomeView.getGuestButton();
        guestButton.addActionListener(e -> {
            // Switch to Guest Mode
            UserProfile guestProfile = new UserProfile(new GuestUser(), "Guest");
            userProfileViewModel.setState(guestProfile);
            guestUserDAO.switchToGuestMode();
            navigateToSearchRecipeView();
        });

        JButton loginSignupButton = welcomeView.getLoginSignupButton();
        loginSignupButton.addActionListener(e -> {
            // Switch to Login View
            switchToView(createLoginView());
        });

        return welcomeView;
    }

    private LoginView createLoginView() {
        return new LoginView(loginController, viewManagerModel);
    }

    private SignupView createSignupView() {
        return new SignupView(signupController, viewManagerModel);
    }

    private SetupView createSetupView() {
        return new SetupView(userProfileViewModel, viewManagerModel);
    }

    private ProfileView createProfileView() {
        ProfileView profileView = new ProfileView(userProfileViewModel, viewManagerModel);

        // Add action listeners directly to the buttons in ProfileView
        JButton changePasswordButton = profileView.getChangePasswordButton();
        changePasswordButton.addActionListener(e -> switchToChangePasswordView());

        JButton changeDisplayNameButton = profileView.getChangeDisplayNameButton();
        changeDisplayNameButton.addActionListener(e -> switchToChangeDisplayNameView());

        return profileView;
    }

    private void switchToChangePasswordView() {
        if (!(userProfileViewModel.getState().getUser() instanceof GuestUser)) {
            ChangePasswordView changePasswordView = new ChangePasswordView(userProfileViewModel, changePasswordController, viewManagerModel);
            switchToView(changePasswordView);
        } else {
            JOptionPane.showMessageDialog(frame, "Guest users cannot change password.", "Access Denied", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void switchToChangeDisplayNameView() {
        if (!(userProfileViewModel.getState().getUser() instanceof GuestUser)) {
            ChangeDisplayNameView changeDisplayNameView = new ChangeDisplayNameView(userProfileViewModel);
            switchToView(changeDisplayNameView);
        } else {
            JOptionPane.showMessageDialog(frame, "Guest users cannot change display name.", "Access Denied", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void navigateToSearchRecipeView() {
        switchToView(searchRecipeView);
    }

    private void switchToView(JPanel view) {
        frame.setContentPane(view);
        frame.revalidate();
        frame.repaint();
    }
}