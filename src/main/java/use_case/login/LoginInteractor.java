package use_case.login;

import app.SessionManager;
import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String inputUsername = loginInputData.getUsername();
        final String inputPassword = loginInputData.getPassword();

        // check if the username exists
        if (!userDataAccessObject.existsByName(inputUsername)) {
            loginPresenter.prepareFailView("Username does not exist");
        }
        // check if the inputPassword is correct
        else {
            final User user = userDataAccessObject.getUser(loginInputData.getUsername());
            // the api puts the expected password into user so we check
            final String expectedPassword = user.getPassword();
            if (!inputPassword.equals(expectedPassword)) {
                loginPresenter.prepareFailView("Incorrect inputPassword");
                return;
            }
            // So inputUsername and inputPassword are correct
            // Set the current user in the session manager
            final SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setCurrentUser(user);
            loginPresenter.prepareSuccessView();
        }
    }
}

