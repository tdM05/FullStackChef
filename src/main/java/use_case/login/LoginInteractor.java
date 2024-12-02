package use_case.login;

import app.SessionUser;
import entity.User;

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
                final User user = userDataAccessObject.get(loginInputData.getUsername());

                userDataAccessObject.setCurrentUsername(user.getName());

                // Set the SessionUser instance
                SessionUser.getInstance().setUser(user);
                System.out.println("SessionUser username: " + SessionUser.getInstance().getUser().getName());
                System.out.println("SessionUser password: " + SessionUser.getInstance().getUser().getPassword());
                System.out.println("SessionUser Diets: " + SessionUser.getInstance().getUser().getDietaryRestrictions());

                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
