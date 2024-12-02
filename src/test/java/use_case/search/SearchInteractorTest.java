package use_case.search;

import entity.CommonRecipe;
import entity.Recipe;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchInteractorTest {

    /**
     * Tests the `execute` method of the SearchInteractor with a valid search query.
     * Mocks a data access object and a presenter to validate the behavior of the interactor.
     */
    @Test
    public void execute_withValidInput() {
        // Mock Data Access
        SearchDataAccessInterface dataAccess = new SearchDataAccessInterface() {
            @Override
            public List<Recipe> getRecipes(List<String> ingredients, int count) {
                List<String> expectedIngredients = List.of("chicken", "garlic", "spinach");
                assertTrue(ingredients.containsAll(expectedIngredients));
                List<Recipe> res = new ArrayList<>();
                res.add(new CommonRecipe(1, "recipeName", "imageUrl", "imageType",
                        new ArrayList<>(), null, new ArrayList<>(), false));
                return res;
            }
        };

        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<SearchOutputData> message) {
                List<SearchOutputData> expectedOutputData = List.of(
                        new SearchOutputData(1, "recipeName", "imageUrl")
                );

                assertEquals(expectedOutputData.get(0).getRecipeId(), message.get(0).getRecipeId());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Failure view should not be called with valid input.");
            }
        };

        SearchInteractor interactor = new SearchInteractor(dataAccess, presenter);

        SearchInputData inputData = new SearchInputData("chicken,  garlic,spinach");
        interactor.execute(inputData);
    }

    /**
     * Tests the `execute` method when the data access throws an exception.
     */
    @Test
    public void execute_withException() {
        // Mock Data Access that throws an exception
        SearchDataAccessInterface dataAccess = new SearchDataAccessInterface() {
            @Override
            public List<Recipe> getRecipes(List<String> ingredients, int count) {
                throw new JSONException("Mock Exception");
            }
        };

        // Mock Presenter
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<SearchOutputData> message) {
                fail("Success view should not be called when an exception occurs.");
            }

            @Override
            public void prepareFailView(String message) {
                // Validate the error message
                assertEquals("Failed to get recipes.", message);
            }
        };

        // Create Interactor
        SearchInteractor interactor = new SearchInteractor(dataAccess, presenter);

        // Input Data
        SearchInputData inputData = new SearchInputData("chicken,  garlic,spinach");

        // Execute Use Case
        interactor.execute(inputData);
    }
}
