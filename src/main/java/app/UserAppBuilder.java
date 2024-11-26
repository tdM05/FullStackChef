//package app;
//
//import data_access.UserAuthDataAccessObject;
//import entity.GuestUser;
//import entity.UserProfile;
//import entity.UserProfileFactory;
//import interface_adapter.UserProfileViewModel;
//import interface_adapter.change_password.ChangePasswordController;
//import use_case.DataAccessException;
//import view.*;
//
//import javax.swing.*;
//
//public class UserAppBuilder {
//    private JFrame frame;
//    private final UserAuthDataAccessObject userDAO;
//    private final UserProfileViewModel userProfileViewModel;
//    private final SearchRecipeView searchRecipeView;
//    private ChangePasswordController changePasswordController;
//
//    public UserAppBuilder(UserProfileFactory userFactory, SearchRecipeView searchRecipeView) {
//        this.userDAO = new UserAuthDataAccessObject(userFactory);
//        this.userProfileViewModel = new UserProfileViewModel();
//        this.searchRecipeView = searchRecipeView;
//    }
//
//    public UserAppBuilder setChangePasswordController(ChangePasswordController controller) {
//        this.changePasswordController = controller;
//        return this;
//    }
//
//    public JFrame build() {
//        frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//
//        // Initialize views
//        WelcomeView welcomeView = new WelcomeView();
//        LoginView loginView = new LoginView();
//        SignupView signupView = new SignupView();
//        SetupView setupView = new SetupView(userProfileViewModel);
//        ProfileView profileView = new ProfileView(userProfileViewModel);
//
//        // Setup navigation
//        welcomeView.addGuestListener(() -> navigateAsGuest());
//        welcomeView.addLoginSignupListener(() -> switchToView(loginView));
//
//        loginView.addLoginListener(() -> authenticateUser(loginView));
//        loginView.addSignupListener(() -> switchToView(signupView));
//
//        signupView.addSignupListener(() -> {
//            String username = signupView.getUsername();
//            String password = signupView.getPassword();
//            UserProfile newUser = new UserProfileFactory().create(username, password);
//            userDAO.saveUser(newUser);
//            userProfileViewModel.setState(newUser);
//            switchToView(setupView);
//        });
//
//        setupView.addNextListener(this::navigateToSearchRecipeView);
//
////        profileView.addChangePasswordListener(() -> switchToChangePasswordView());
////        profileView.addChangeDisplayNameListener(() -> switchToChangeDisplayNameView());
////
////        searchRecipeView.addProfileButtonListener(() -> switchToView(profileView));
//
//        // Start with WelcomeView
//        switchToView(welcomeView);
//
//        return frame;
//    }
//
//    private void switchToView(JPanel view) {
//        frame.setContentPane(view);
//        frame.revalidate();
//        frame.repaint();
//    }
//
//    private void navigateAsGuest() {
//        UserProfile guestProfile = new UserProfile(new GuestUser(), "Guest");
//        userProfileViewModel.setState(guestProfile);
//        navigateToSearchRecipeView();
//    }
//
//    // Navigate directly to SearchRecipeView after login or signup.
//    private void authenticateUser(LoginView loginView) {
//        String username = loginView.getUsername();
//        String password = loginView.getPassword();
//        try {
//            if (userDAO.authenticateUser(username, password)) {
//                UserProfile user = userDAO.loadUser(username);
//                userProfileViewModel.setState(user);
//                navigateToSearchRecipeView();
//            } else {
//                loginView.displayError("Invalid username or password.");
//            }
//        } catch (DataAccessException e) {
//            loginView.displayError("Error: " + e.getMessage());
//        }
//    }
//
//    private void signupUser(SignupView signupView) {
//        String username = signupView.getUsername();
//        String password = signupView.getPassword();
//        String repeatPassword = signupView.getRepeatPassword();
//
//        if (!password.equals(repeatPassword)) {
//            signupView.displayError("Passwords do not match.");
//            return;
//        }
//
//        try {
//            // Create the user and transition to setup
//            UserProfile newUser = new UserProfileFactory().create(username, password);
//            userProfileViewModel.setState(newUser);
//            switchToView(new SetupView(userProfileViewModel, this::handleSetupViewNext));
//        } catch (DataAccessException e) {
//            signupView.displayError("Error: " + e.getMessage());
//        }
//    }
//
//    private void handleSetupViewNext(String displayName) {
//        // Fetch the display name and update the user profile
//        UserProfile currentUser = userProfileViewModel.getState();
//        currentUser.setDisplayName(displayName);
//
//        try {
//            // Persist the updated profile
//            userDAO.saveUser(currentUser);
//            navigateToSearchRecipeView();
//        } catch (DataAccessException e) {
//            System.err.println("Error saving user: " + e.getMessage());
//        }
//    }
//
//
//    private void navigateToSearchRecipeView() {
//        String displayName = userProfileViewModel.getState().getDisplayName();
//        if (displayName != null && !displayName.isEmpty()) {
//            System.out.println("Welcome, " + displayName + "!");
//        } else {
//            System.out.println("Welcome, guest!");
//        }
//        switchToView(searchRecipeView);
//    }
//
//}
