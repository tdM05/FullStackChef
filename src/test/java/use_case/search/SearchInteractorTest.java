package use_case.search;

import entity.CommonRecipe;
import entity.Recipe;
import org.junit.Test;

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
            public List<Recipe> getRecipes(List<String> ingredients, int count) {
                // notice that ingredients is the recipe list that the interactor gave it, so it should be consistent
                List<String> expectedIngredients = new ArrayList<>();
                expectedIngredients.add("chicken");
                expectedIngredients.add("garlic");
                expectedIngredients.add("spinach");
                assertEquals(ingredients, expectedIngredients);

                // This is the pre-determined recipe that the interactor will return
                List<Recipe> res = new ArrayList<>();
                res.add(new CommonRecipe(1, "recipeName", "imageUrl", "imageType",
                        new ArrayList<>(), null, new ArrayList<>(), false));
                return res;
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
        SearchInteractor interactor = new SearchInteractor(dataAccess, presenter);

        SearchInputData inputData = new SearchInputData("chicken,  garlic,spinach");
        interactor.execute(inputData);
    }
}