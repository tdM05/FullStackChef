package use_case.change_password;

import entity.CommonUser;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInteractorTest {

    private ChangePasswordUserDataAccessInterface userDataAccessObject;
    private ChangePasswordOutputBoundary userPresenter;
    private ChangePasswordInteractor interactor;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the test user
        testUser = new CommonUser("testUser", "oldPassword");

        // Use actual implementation objects for the DAO and Presenter interfaces
        userDataAccessObject = new MockChangePasswordUserDataAccess();
        userPresenter = new MockChangePasswordPresenter();

        // Initialize the interactor
        interactor = new ChangePasswordInteractor(userDataAccessObject, userPresenter);
    }

    @Test
    void testExecuteWithValidData() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("testUser", "newPassword");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteWithSamePassword() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("testUser", "oldPassword");

        userPresenter = new MockChangePasswordPresenter() {
            @Override
            public void prepareFailView(String error) {
                assertEquals("The new password cannot be the same as original password", error);
            }
        };

        interactor = new ChangePasswordInteractor(userDataAccessObject, userPresenter);
        interactor.execute(inputData);
    }

    // Mock classes to adhere to the Dependency Inversion Principle
    private static class MockChangePasswordUserDataAccess implements ChangePasswordUserDataAccessInterface {
        private User user;

        @Override
        public void changePassword(User user) {
            this.user = user;
        }

        @Override
        public User getUserByUsername(String username) {
            return new CommonUser(username, "oldPassword");
        }
    }

    private static class MockChangePasswordPresenter implements ChangePasswordOutputBoundary {
        @Override
        public void prepareSuccessView(ChangePasswordOutputData outputData) {
            assertEquals("testUser", outputData.getUsername());
            assertFalse(outputData.isUseCaseFailed());
        }

        @Override
        public void prepareFailView(String error) {
            fail("Test failed with error: " + error);
        }
    }
}