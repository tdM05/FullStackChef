package use_case.signup;

import entity.CommonUser;
import entity.UserFactory;

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
            userPresenter.prepareFailView("CommonUser already exists.");
        }
        // If password not match with repetition
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Password don't match.");
        }
        // CommonUser created
        else {
            final CommonUser commonUser = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(commonUser);

            final SignupOutputData signupOutputData = new SignupOutputData(commonUser.getName(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginview() {
        userPresenter.switchToLoginView();
    }
}
