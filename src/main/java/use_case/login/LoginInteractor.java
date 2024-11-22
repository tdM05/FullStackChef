package use_case.login;

import entity.CommonUser;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary){
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData){
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)){
            loginPresenter.prepareFailView("Username does not exist");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();

            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password");
            }
            else {
                final CommonUser commonUser = userDataAccessObject.get(loginInputData.getUsername());
                userDataAccessObject.setCurrentUsername(commonUser.getName());

                final LoginOutputData loginOutputData = new LoginOutputData(commonUser.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}