package use_case.display_history;

import app.SessionUser;
import entity.CommonRecipe;
import entity.CommonUser;
import entity.Recipe;
import entity.User;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisplayHistoryInteractorTest {

    private DisplayHistoryDataAccessInterface dataAccess;
    private DisplayHistoryOutputBoundary presenter;
    private DisplayHistoryInteractor interactor;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the test user
        testUser = new CommonUser("testUser", "password");

        // Mock the session user
        SessionUser session = SessionUser.getInstance();
        session.setUser(testUser);

        // Mock the data access interface
        dataAccess = new MockDisplayHistoryDataAccess();

        // Mock the presenter
        presenter = new MockDisplayHistoryPresenter();

        // Initialize the interactor
        interactor = new DisplayHistoryInteractor(dataAccess, presenter);
    }

    @Test
    void testExecuteWithValidData() {
        interactor.execute();
    }

    @Test
    void testExecuteWithNoUser() {
        // Set the session user to null
        SessionUser.getInstance().setUser(null);

        presenter = new MockDisplayHistoryPresenter() {
            @Override
            public void prepareFailView(String error) {
                assertEquals("Error with retrieving user", error);
            }
        };

        interactor = new DisplayHistoryInteractor(dataAccess, presenter);
        interactor.execute();
    }

    @Test
    void testExecuteWithException() {
        dataAccess = new MockDisplayHistoryDataAccess() {
            @Override
            public List<Recipe> getRecipes(String recipeIds) throws IOException, JSONException {
                throw new IOException("Mock IOException");
            }
        };

        presenter = new MockDisplayHistoryPresenter() {
            @Override
            public void prepareFailView(String error) {
                assertEquals("Error with retrieving recipes", error);
            }
        };

        interactor = new DisplayHistoryInteractor(dataAccess, presenter);
        interactor.execute();
    }

    @Test
    void testSwitchToSearchView() {
        interactor.switchToSearchView();
        // Add assertions or verifications for switchToSearchView if needed
    }

    // Mock classes to adhere to the Dependency Inversion Principle
    private static class MockDisplayHistoryDataAccess implements DisplayHistoryDataAccessInterface {
        @Override
        public List<Integer> getHistory(User user) {
            List<Integer> history = new ArrayList<>();
            history.add(1);
            history.add(2);
            return history;
        }

        @Override
        public List<Recipe> getRecipes(String recipeIds) throws IOException, JSONException {
            List<Recipe> recipes = new ArrayList<>();
            recipes.add(new CommonRecipe(1, "Recipe 1", "image1.jpg",
                    "jpg", Collections.emptyList(), null, Collections.emptyList(), false));
            recipes.add(new CommonRecipe(2, "Recipe 2", "image2.jpg",
                    "jpg", Collections.emptyList(), null, Collections.emptyList(), false));
            return recipes;
        }
    }

    private static class MockDisplayHistoryPresenter implements DisplayHistoryOutputBoundary {
        @Override
        public void prepareSuccessView(List<DisplayHistoryOutputData> outputData) {
            assertEquals(2, outputData.size());
            assertEquals("Recipe 1", outputData.get(0).getRecipeName());
            assertEquals("Recipe 2", outputData.get(1).getRecipeName());
        }

        @Override
        public void prepareFailView(String error) {
            fail("Test failed with error: " + error);
        }

        @Override
        public void switchToSearchView() {
            // Mock implementation for switchToSearchView
        }
    }
}