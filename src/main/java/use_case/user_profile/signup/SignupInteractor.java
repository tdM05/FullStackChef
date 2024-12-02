package use_case.user_profile.signup;

import entity.User;
import entity.UserFactory;
import use_case.DataAccessException;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) throws DataAccessException {
        try {
            validateInput(signupInputData);

            User newUser = createUser(signupInputData);
            saveUser(newUser);

            prepareSuccessResponse(newUser);
        } catch (IllegalArgumentException e) {
            prepareFailureResponse(e.getMessage());
        } catch (DataAccessException e) {
            prepareFailureResponse("Failed to save user profile: " + e.getMessage());
        }
    }

    private void validateInput(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords don't match.");
        }

        if (signupInputData.getPassword().isEmpty() || signupInputData.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
    }

    private User createUser(SignupInputData signupInputData) {
        return userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
    }

    private void saveUser(User user) throws DataAccessException {
        userDataAccessObject.save(user);
    }

    private void prepareSuccessResponse(User user) {
        SignupOutputData signupOutputData = new SignupOutputData(user.getName(), false);
        userPresenter.prepareSuccessView(signupOutputData);
    }

    private void prepareFailureResponse(String errorMessage) {
        userPresenter.prepareFailView(errorMessage);
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
