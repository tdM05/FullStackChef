package use_case.user_profile.signup;

import entity.user_profile.User;
import entity.user_profile.UserFactory;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupUserDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory){
        this.userDataAccessObject = signupUserDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData){
        // If username already exists (not unique)
        if (userDataAccessObject.existsByName(signupInputData.getUsername())){
            userPresenter.prepareFailView("Username already exists. Please choose other username.");
        }
        // If password not match with repetition
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Password don't match.");
        }
        // User created
        else {
            final User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginview() {
        userPresenter.switchToLoginView();
    }
}
