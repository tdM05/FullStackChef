package use_case.login;

import app.SessionManager;
import data_access.UserProfile.UserProfileDao;
import entity.User;
import interface_adapter.login.LoginPresenter;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginInteractorTest {

    public void LoginWithTestUser() {
        LoginUserDataAccessInterface dao = new UserProfileDao();
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // verify session manager has correct user
                SessionManager session = SessionManager.getInstance();
                User user = session.getCurrentUser();
                assertEquals(user.getName(), "test_user");
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
        LoginInteractor interactor = new LoginInteractor(dao, presenter);
        interactor.execute(new LoginInputData("test_user", "123"));
    }
    /**
     * Logs in user test_user with password 123
     */
    @Test
    public void execute() {
        LoginUserDataAccessInterface dao = new UserProfileDao();
        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // verify session manager has correct user
                SessionManager session = SessionManager.getInstance();
                User user = session.getCurrentUser();
                assertEquals(user.getName(), "test_user");
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
        LoginInteractor interactor = new LoginInteractor(dao, presenter);
        interactor.execute(new LoginInputData("test_user", "123"));
    }
}