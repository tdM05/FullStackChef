package app;

import data_access.FavoriteDataAccessObject;
import data_access.RecipeDataAccessObject;
import data_access.user_profile.InMemoryGuestUserDataAccessObject;
import data_access.user_profile.UserAuthDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.check_favorite.CheckFavoriteController;
import interface_adapter.check_favorite.CheckFavoritePresenter;
import interface_adapter.display_favorites.DisplayFavoriteViewModel;
import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipePresenter;
import interface_adapter.display_recipe.DisplayRecipeViewModel;
import interface_adapter.user_profile.UserProfileViewModel;
import interface_adapter.user_profile.change_password.ChangePasswordController;
import interface_adapter.user_profile.change_password.LoggedInViewModel;
import interface_adapter.user_profile.login.LoginController;
import interface_adapter.user_profile.login.LoginPresenter;
import interface_adapter.user_profile.login.LoginViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.user_profile.logout.LogoutController;
import interface_adapter.user_profile.logout.LogoutPresenter;
import interface_adapter.user_profile.profile.ProfileController;
import interface_adapter.user_profile.profile.ProfilePresenter;
import interface_adapter.user_profile.setup.SetupController;
import interface_adapter.user_profile.setup.SetupPresenter;
import interface_adapter.user_profile.signup.SignupController;
import interface_adapter.user_profile.signup.SignupPresenter;
import interface_adapter.user_profile.signup.SignupViewModel;
import use_case.check_favorite.CheckFavoriteInputBoundary;
import use_case.check_favorite.CheckFavoriteInteractor;
import use_case.check_favorite.CheckFavoriteOutputBoundary;
import use_case.display_recipe.DisplayRecipeInputBoundary;
import use_case.display_recipe.DisplayRecipeInteractor;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.user_profile.change_password.ChangePasswordInputBoundary;
import use_case.user_profile.login.LoginInputBoundary;
import use_case.user_profile.login.LoginInteractor;
import use_case.user_profile.login.LoginOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import use_case.user_profile.login.LoginUserDataAccessInterface;
import use_case.user_profile.logout.LogoutInputBoundary;
import use_case.user_profile.logout.LogoutInteractor;
import use_case.user_profile.logout.LogoutOutputBoundary;
import use_case.user_profile.logout.LogoutUserDataAccessInterface;
import use_case.user_profile.profile.ProfileInputBoundary;
import use_case.user_profile.profile.ProfileInteractor;
import use_case.user_profile.profile.ProfileOutputBoundary;
import use_case.user_profile.signup.SignupOutputBoundary;
import use_case.user_profile.signup.SignupInputBoundary;
import use_case.user_profile.signup.SignupInteractor;
import use_case.user_profile.signup.SignupUserDataAccessInterface;
import view.*;

import view.user_profile.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class MainAppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private UserFactory userFactory = new CommonUserFactory();
    private ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager;

    private final UserAuthDataAccessObject userDAO = new UserAuthDataAccessObject(userFactory);
    private final InMemoryGuestUserDataAccessObject guestUserDAO = new InMemoryGuestUserDataAccessObject();
    private final RecipeDataAccessObject recipeDataAccessObject = new RecipeDataAccessObject();
    private final FavoriteDataAccessObject favoriteDataAccessObject = new FavoriteDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private DisplayRecipeView displayRecipeView;
    private DisplayRecipeViewModel displayRecipeViewModel;
    private DisplayFavoriteView displayFavoriteView;
    private DisplayFavoriteViewModel displayFavoriteViewModel;
    private UserProfileViewModel userProfileViewModel;

    private WelcomeView welcomeView;
    private SetupView setupView;
    private ChangePasswordView changePasswordView;
    private ChangeDisplayNameView changeDisplayNameView;
    private ProfileView profileView;

    public MainAppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public MainAppBuilder addWelcomeView() {
        welcomeView = new WelcomeView(viewManagerModel);
        cardPanel.add(welcomeView, welcomeView.getViewName());
        return this;

    }

    /**
     * Adds the SignupView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();

        // Initialize SignupController with two arguments
        SignupController signupController = new SignupController(
                new SignupInteractor(userDAO,
                        new SignupPresenter(viewManagerModel, signupViewModel), userFactory),
                viewManagerModel  // Passing the second argument (ViewManagerModel)
        );

        signupView = new SignupView(signupController, viewManagerModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the LoginView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        LoginController loginController = new LoginController(
                new LoginInteractor(userDAO,
                        new LoginPresenter(viewManagerModel, loginViewModel, searchViewModel)));
        loginView = new LoginView(loginController, loginViewModel, viewManagerModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public MainAppBuilder addSetupView() {
        // Ensure UserProfileViewModel is instantiated
        userProfileViewModel = new UserProfileViewModel();
        SetupPresenter setupPresenter = new SetupPresenter(userProfileViewModel, viewManagerModel);
        SetupController setupController = new SetupController(setupPresenter);
        setupView = new SetupView(setupController, userProfileViewModel);

        cardPanel.add(setupView, "setupView");
        return this;
    }


    public MainAppBuilder addProfileView() {
        UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
        ProfileView profileView = new ProfileView(userProfileViewModel, viewManagerModel);
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    public MainAppBuilder addChangePasswordView() {
        UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
        ChangePasswordController changePasswordController = new ChangePasswordController(userDAO);
        ChangePasswordView changePasswordView = new ChangePasswordView(userProfileViewModel, changePasswordController, viewManagerModel);
        cardPanel.add(changePasswordView, changePasswordView.getViewName());
        return this;
    }

    public MainAppBuilder addChangeDisplayNameView() {
        changeDisplayNameView = new ChangeDisplayNameView(userProfileViewModel, viewManagerModel);
        cardPanel.add(changeDisplayNameView, changeDisplayNameView.getViewName());
        return this;
    }

    private void switchToView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }

    private void switchToGuestMode() {
        GuestUser guestUser = new GuestUser();
        SessionUser.getInstance().setUser(guestUser);

        UserProfile guestUserProfile = new UserProfile(guestUser, guestUser.getDisplayName());
        userProfileViewModel.setState(guestUserProfile);
        guestUserDAO.switchToGuestMode();

        switchToView("searchView");
    }

    /**
     * Adds the SearchView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        searchView.setPreferredSize(new Dimension(1200, 800));

        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    /**
     * Adds the DisplayRecipeView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addDisplayRecipeView() {
        displayRecipeViewModel = new DisplayRecipeViewModel();
        displayRecipeView = new DisplayRecipeView(displayRecipeViewModel);
        displayRecipeView.setPreferredSize(new Dimension(1200, 800));
        cardPanel.add(displayRecipeView, displayRecipeView.getViewName());
        return this;
    }

    /**
     * Adds the FavoriteView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addFavoriteView() {
        displayFavoriteViewModel = new DisplayFavoriteViewModel();
        displayFavoriteView = new DisplayFavoriteView(displayFavoriteViewModel);
        cardPanel.add(displayFavoriteView, displayFavoriteView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDAO, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor, viewManagerModel);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel,
                searchViewModel);
        final LoginInputBoundary userLoginInteractor = new LoginInteractor(
                userDAO, loginOutputBoundary);

        final LoginController controller = new LoginController(userLoginInteractor);
        loginView.setLoginController(controller);
        return this;
    }

    public MainAppBuilder addProfileUseCase() {
        profileView = new ProfileView(userProfileViewModel, viewManagerModel);
        final ProfileOutputBoundary profileOutputBoundary = new ProfilePresenter(viewManagerModel);
        final ProfileInputBoundary profileInputBoundary = new ProfileInteractor(userDAO, profileOutputBoundary);
        final ProfileController profileController = new ProfileController(profileInputBoundary, viewManagerModel);
        searchView.getProfile().setProfileController(profileController);
        cardPanel.add(profileView, profileView.getViewName());

        return this;
    }

    public MainAppBuilder addLogoutUseCase() {
        profileView = new ProfileView(userProfileViewModel, viewManagerModel);
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(
                new LoggedInViewModel(), viewManagerModel, loginViewModel);
        final LogoutInputBoundary logoutInputBoundary = new LogoutInteractor(userDAO, logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInputBoundary, viewManagerModel);
        searchView.getProfile().setLogoutController(logoutController);
        cardPanel.add(profileView, welcomeView.getViewName());

        return this;
    }

    /**
     * Adds the Search Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(recipeDataAccessObject, searchOutputBoundary);

        final SearchController searchController = new SearchController(searchInteractor);
        searchView.setSearchController(searchController);
        return this;
    }

    /**
     * Adds the DisplayRecipe Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addDisplayRecipeUseCase() {
        final DisplayRecipeOutputBoundary displayRecipeOutputBoundary = new DisplayRecipePresenter(viewManagerModel, searchViewModel, displayRecipeViewModel);
        final DisplayRecipeInputBoundary displayRecipeInteractor = new DisplayRecipeInteractor(recipeDataAccessObject, displayRecipeOutputBoundary);

        final DisplayRecipeController displayRecipeController = new DisplayRecipeController(displayRecipeInteractor);
        searchView.setDisplayRecipeController(displayRecipeController);
        displayRecipeView.setDisplayRecipeController(displayRecipeController);
        return this;
    }

    /**
     * Adds the CheckFavorite Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addCheckFavoriteUseCase() {
        final CheckFavoriteOutputBoundary checkFavoriteOutputBoundary = new CheckFavoritePresenter(viewManagerModel, searchViewModel, displayRecipeViewModel);
        final CheckFavoriteInputBoundary checkFavoriteInteractor = new CheckFavoriteInteractor(favoriteDataAccessObject, checkFavoriteOutputBoundary);

        final CheckFavoriteController checkFavoriteController = new CheckFavoriteController(checkFavoriteInteractor);
        searchView.setCheckFavoriteController(checkFavoriteController);
        return this;
    }

    /**
     * Adds the Favorite Use Case to the application.
     * @return this AppBuilder
     */


    /**
     * Builds the application.
     * @return the built application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Recipe App");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Start with WelcomeView
        viewManagerModel.setState("welcomeView");  // This ensures WelcomeView is loaded first
        viewManagerModel.firePropertyChanged();  // Ensure the view is updated immediately

        viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel, application);

        application.setSize(new Dimension(380, 250));
        application.setResizable(true);
        application.setLocationRelativeTo(null);

        return application;
    }
}
