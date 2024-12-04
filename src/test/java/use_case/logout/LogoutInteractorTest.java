package use_case.logout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    private LogoutUserDataAccessInterface userDataAccessObject;
    private LogoutOutputBoundary logoutPresenter;
    private LogoutInteractor interactor;

    @BeforeEach
    void setUp() {
        // Use actual implementation objects for the DAO and Presenter interfaces
        userDataAccessObject = new MockLogoutUserDataAccess();
        logoutPresenter = new MockLogoutPresenter();

        // Initialize the interactor
        interactor = new LogoutInteractor(userDataAccessObject, logoutPresenter);
    }

    @Test
    void testExecuteWithValidData() {
        LogoutInputData inputData = new LogoutInputData("testUser");
        interactor.execute(inputData);
    }

    // Mock classes to adhere to the Dependency Inversion Principle
    private static class MockLogoutUserDataAccess implements LogoutUserDataAccessInterface {
        private String currentUsername;

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public void setCurrentUsername(String username) {
            this.currentUsername = username;
        }
    }

    private static class MockLogoutPresenter implements LogoutOutputBoundary {
        @Override
        public void prepareSuccessView(LogoutOutputData outputData) {
            assertEquals("testUser", outputData.getUsername());
            assertFalse(outputData.isUseCaseFailed());
        }

        @Override
        public void prepareFailView(String error) {
            fail("Test failed with error: " + error);
        }
    }
}