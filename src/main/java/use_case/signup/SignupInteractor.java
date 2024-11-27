package use_case.signup;

import app.SessionManager;
import data_access.UserProfile.ProfileException;
import entity.CommonUser;
import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupUserDataAccessInterface signupUserDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary){
        this.userDataAccessObject = signupUserDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        // If username already exists (not unique)
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("CommonUser already exists.");
        }
        // If password not match with repetition
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Password don't match.");
        }
        // CommonUser created
        else {
            // create user and save the user into the api

            try {
                userDataAccessObject.save(signupInputData.getUsername(),
                        signupInputData.getPassword());
            }
            catch (ProfileException ex) {
                userPresenter.prepareFailView(ex.getMessage());
                return;
            }
            final User user = new CommonUser(signupInputData.getUsername(), signupInputData.getPassword());
            // Then if everything is good, we save the user to the session.
            final SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.setCurrentUser(user);
            userPresenter.prepareSuccessView();
        }
    }
}
