package app.builders;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import view.LoginView;

import javax.swing.*;

/**
 * Builder for the Login app.
 */
public class LoginAppBuilder {
    private LoginUserDataAccessInterface loginDAO;
    private LoginViewModel loginViewModel = new LoginViewModel();
    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private ViewManagerModel viewManagerModel = new ViewManagerModel();

    /**
     * Adds the Login DAO to the app.
     * @param dao the Login DAO
     * @return this
     */
    public LoginAppBuilder addLoginDAO(LoginUserDataAccessInterface dao) {
        this.loginDAO = dao;
        return this;
    }

    /**
     * Adds the Login view to the app.
     * @param currentViewManagerModel the current view manager model
     * @return this
     */
    public LoginAppBuilder addLoginView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = new ViewManagerModel();
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, currentViewManagerModel);
        return this;
    }

    /**
     * Adds the Login use case.
     * @return this
     * @throws IllegalStateException if the Login view has not been added.
     */
    public LoginAppBuilder addLoginUseCase() throws IllegalStateException {
        final LoginOutputBoundary output = new LoginPresenter(loginViewModel, viewManagerModel);
        loginInteractor = new LoginInteractor(loginDAO, output);

        final LoginController loginController = new LoginController(loginInteractor);

        if (loginView == null) {
            throw new IllegalStateException("You must add the Login view before adding the use case.");
        }

        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Builds the Login app into a JFrame.
     * @return the Login app
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);

        frame.add(loginView);
        return frame;
    }
}