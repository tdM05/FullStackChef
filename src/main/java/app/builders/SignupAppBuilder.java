package app.builders;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.SignUpView;

import javax.swing.*;

/**
 * Builder for the Signup app.
 */
public class SignupAppBuilder {
    private SignupUserDataAccessInterface signupDAO;
    private SignupViewModel signupViewModel = new SignupViewModel();
    private SignUpView signupView;
    private SignupInteractor signupInteractor;
    private ViewManagerModel viewManagerModel = new ViewManagerModel();

    /**
     * Adds the Signup DAO to the app.
     * @param dao the Signup DAO
     * @return this
     */
    public SignupAppBuilder addSignupDAO(SignupUserDataAccessInterface dao) {
        this.signupDAO = dao;
        return this;
    }

    /**
     * Adds the Signup view to the app.
     * @param currentViewManagerModel the current view manager model
     * @return this
     */
    public SignupAppBuilder addSignupView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = new ViewManagerModel();
        signupViewModel = new SignupViewModel();
        signupView = new SignUpView(signupViewModel, currentViewManagerModel);
        return this;
    }

    /**
     * Adds the Signup use case.
     * @return this
     * @throws IllegalStateException if the Signup view has not been added.
     */
    public SignupAppBuilder addSignupUseCase() throws IllegalStateException {
        final SignupOutputBoundary output = new SignupPresenter(viewManagerModel, signupViewModel);
        signupInteractor = new SignupInteractor(signupDAO, output);

        final SignupController signupController = new SignupController(signupInteractor);

        if (signupView == null) {
            throw new IllegalStateException("You must add the Signup view before adding the use case.");
        }

        signupView.addController(signupController);
        return this;
    }

    /**
     * Builds the Signup app into a JFrame.
     * @return the Signup app
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Signup");
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);

        frame.add(signupView);
        return frame;
    }
}