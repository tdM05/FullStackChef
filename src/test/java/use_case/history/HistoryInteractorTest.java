package use_case.history;

import app.SessionUser;
import entity.CommonUser;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryInteractorTest {

    private HistoryDataAccessInterface dataAccess;
    private HistoryOutputBoundary presenter;
    private HistoryInteractor interactor;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the test user
        testUser = new CommonUser("testUser", "password");

        // Mock the session user
        SessionUser session = SessionUser.getInstance();
        session.setUser(testUser);

        // Use actual implementation objects for the DAO and Presenter interfaces
        dataAccess = new MockHistoryDataAccess();
        presenter = new MockHistoryPresenter();

        // Initialize the interactor
        interactor = new HistoryInteractor(dataAccess, presenter);
    }

    @Test
    void testExecuteWithValidData() {
        HistoryInputData inputData = new HistoryInputData(1);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteWithNoUser() {
        // Set the session user to null
        SessionUser.getInstance().setUser(null);

        presenter = new MockHistoryPresenter() {
            @Override
            public void prepareFailView(String error) {
                assertEquals("Error with retrieving user", error);
            }
        };

        interactor = new HistoryInteractor(dataAccess, presenter);
        HistoryInputData inputData = new HistoryInputData(1);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteWithExistingRecipe() {
        HistoryInputData inputData = new HistoryInputData(2);
        interactor.execute(inputData);
    }

    // Mock classes to adhere to the Dependency Inversion Principle
    private static class MockHistoryDataAccess implements HistoryDataAccessInterface {
        private List<Integer> history = new ArrayList<>();

        @Override
        public List<Integer> getHistory(User user) {
            history.add(1);
            history.add(2);
            return history;
        }

        @Override
        public void saveHistory(User user, List<Integer> history) {
            this.history = history;
        }
    }

    private static class MockHistoryPresenter implements HistoryOutputBoundary {
        @Override
        public void prepareSuccessView(HistoryOutputData outputData) {
            assertTrue(outputData.getHistory());
        }

        @Override
        public void prepareFailView(String error) {
            fail("Test failed with error: " + error);
        }
    }
}