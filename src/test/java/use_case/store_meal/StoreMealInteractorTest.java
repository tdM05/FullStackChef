package use_case.store_meal;

import app.SessionManager;
import data_access.UserProfile.UserProfileDao;
import entity.CommonUser;
import entity.User;
import org.junit.Test;
import use_case.login.LoginInputData;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;

import static org.junit.Assert.*;

public class StoreMealInteractorTest {
    @Test
    public void execute() {
        StoreMealInputData inputData = new StoreMealInputData(10);
        UserProfileDao dao = new UserProfileDao();
        StoreMealInteractor interactor = new StoreMealInteractor(dao);

        // log a user in with test_user 123
        LoginOutputBoundary loginPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView() {
                // do nothing
            }
            @Override
            public void prepareFailView(String message) {
                // do nothing
            }
        };
        LoginInteractor loginInteractor = new LoginInteractor(dao, loginPresenter);
        loginInteractor.execute(new LoginInputData("test_user", "123"));

        // Now get the logged in user
        SessionManager sessionManager = SessionManager.getInstance();
        User user = sessionManager.getCurrentUser();

        interactor.execute(inputData, user);

        // Now relogin the user to check if the meal was stored
        loginInteractor.execute(new LoginInputData("test_user", "123"));
        assertTrue(user.getMealIds().contains(10));

    }
}