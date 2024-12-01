package app;

import data_access.DBUserDataAccessObject;
import data_access.FavoriteDataAccessObject;
import data_access.RecipeDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.check_favorite.CheckFavoriteController;
import interface_adapter.check_favorite.CheckFavoritePresenter;
import interface_adapter.display_favorites.DisplayFavoriteController;
import interface_adapter.display_favorites.DisplayFavoritePresenter;
import interface_adapter.display_favorites.DisplayFavoriteViewModel;
import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipePresenter;
import interface_adapter.display_recipe.DisplayRecipeViewModel;

import interface_adapter.favorite.FavoriteController;
import interface_adapter.favorite.FavoritePresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.check_favorite.CheckFavoriteInputBoundary;
import use_case.check_favorite.CheckFavoriteInteractor;
import use_case.check_favorite.CheckFavoriteOutputBoundary;
import use_case.display_favorite.DisplayFavoriteInputBoundary;
import use_case.display_favorite.DisplayFavoriteInteractor;
import use_case.display_favorite.DisplayFavoriteOutputBoundary;
import use_case.display_recipe.DisplayRecipeInputBoundary;
import use_case.display_recipe.DisplayRecipeInteractor;
import use_case.display_recipe.DisplayRecipeOutputBoundary;

import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInteractor;
import use_case.favorite.FavoriteOutputBoundary;
import use_case.favorite.FavoriteOutputData;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

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

    private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);
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

    public MainAppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the SignupView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the LoginView to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        loginView.setPreferredSize(new Dimension(380, 230));
        cardPanel.add(loginView, loginView.getViewName());
        return this;
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
        displayFavoriteView.setPreferredSize(new Dimension(1200, 800));
        cardPanel.add(displayFavoriteView, displayFavoriteView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        //Signup
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
                userDataAccessObject, loginOutputBoundary);

        final LoginController controller = new LoginController(userLoginInteractor);
        //Login
        loginView.setLoginController(controller);
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
        //Search recipe
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
        //Display recipe from search view
        searchView.setDisplayRecipeController(displayRecipeController);
        //Back button
        displayRecipeView.setDisplayRecipeController(displayRecipeController);
        //Display recipe from favorite view
        displayFavoriteView.setDisplayRecipeController(displayRecipeController);
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

        // Check favorite button
        searchView.setCheckFavoriteController(checkFavoriteController);
        // Check favorite button
        displayFavoriteView.setCheckFavoriteController(checkFavoriteController);
        return this;
    }

    /**
     * Adds the Favorite Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addFavoriteUseCase() {
        final FavoriteOutputBoundary favoriteOutputBoundary = new FavoritePresenter(displayRecipeViewModel);
        final FavoriteInputBoundary favoriteInteractor = new FavoriteInteractor(favoriteDataAccessObject, favoriteOutputBoundary);

        final FavoriteController favoriteController = new FavoriteController(favoriteInteractor);
        // Toggle favorite button
        displayRecipeView.setFavoriteController(favoriteController);
        return this;
    }

    /**
     * Adds the DisplayFavorite Use Case to the application.
     * @return this AppBuilder
     */
    public MainAppBuilder addDisplayFavoriteUseCase() {
        final DisplayFavoriteOutputBoundary displayFavoriteOutputBoundary = new DisplayFavoritePresenter(viewManagerModel, searchViewModel, displayFavoriteViewModel);
        final DisplayFavoriteInputBoundary displayFavoriteInteractor = new DisplayFavoriteInteractor(favoriteDataAccessObject, displayFavoriteOutputBoundary);

        final DisplayFavoriteController displayFavoriteController = new DisplayFavoriteController(displayFavoriteInteractor);
        // Display favorites from search view
        searchView.getProfile().setDisplayFavoriteController(displayFavoriteController);
        // Back button
        displayFavoriteView.setDisplayFavoriteController(displayFavoriteController);
        // Display favorites from display recipe view
        displayRecipeView.setDisplayFavoriteController(displayFavoriteController);
        return this;
    }

    /**
     * Builds the application.
     * @return the built application
     */
    public JFrame build() {
        final JFrame application = new JFrame("FullStackChef");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel, application);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.setSize(new Dimension(380, 250));
        application.setResizable(false);
        application.setLocationRelativeTo(null);

        return application;
    }
}
