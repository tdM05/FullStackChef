package use_case.search;

import entity.CommonRecipe;
import entity.CommonDietaryRestriction;
import entity.Recipe;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchInteractorTest {

    /**
     * This test is to test the execute method of the SearchRecipeInteractor.
     * It creates a mock data access object and a mock presenter to test the interactor.
     */
    @Test
    public void execute() {
        SearchDataAccessInterface dataAccess = new SearchDataAccessInterface() {
            @Override
            public List<Recipe> getRecipes(List<String> ingredients, int count) throws IOException, JSONException {
                // Check that ingredients match expectation
                List<String> expectedIngredients = new ArrayList<>();
                expectedIngredients.add("chicken");
                expectedIngredients.add("garlic");
                expectedIngredients.add("spinach");
                assertEquals(expectedIngredients, ingredients);

                // Return a predetermined recipe list
                List<Recipe> res = new ArrayList<>();
                res.add(new CommonRecipe(1, "recipeName", "imageUrl", "imageType",
                        new ArrayList<>(), null, new ArrayList<>(), false));
                return res;
            }

            @Override
            public List<Recipe> getRecipes(List<String> ingredientsList, List<String> dietaryFilters, int number) throws IOException, JSONException {
                // Since we are not testing dietary filters here, just return an empty list or similar.
                return new ArrayList<>();
            }
        };

        DietaryRestrictionDataAccessInterface dietaryDataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
                // Not needed for this test; do nothing
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                // For this test, just return empty dietary restrictions
                return new CommonDietaryRestriction(new ArrayList<>());
            }
        };

        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<SearchOutputData> message) {
                List<SearchOutputData> expectedOutputData = new ArrayList<>();
                expectedOutputData.add(new SearchOutputData(1,"recipeName", "imageUrl"));

                assertEquals(expectedOutputData, message);
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Failed to get recipes.", message);
            }
        };

        // Note: Now we provide all three parameters to the SearchInteractor constructor
        SearchInteractor interactor = new SearchInteractor(dataAccess, dietaryDataAccess, presenter);

        SearchInputData inputData = new SearchInputData("chicken,  garlic,spinach");
        interactor.execute(inputData);
    }
}
