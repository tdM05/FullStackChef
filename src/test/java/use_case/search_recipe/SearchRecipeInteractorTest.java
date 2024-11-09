package use_case.search_recipe;

import data_access.RecipeDataAccessObject;
import entity.CommonRecipe;
import entity.NutritionalInfo;
import entity.Recipe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchRecipeInteractorTest {

    /**
     * This test is to test the execute method of the SearchRecipeInteractor.
     * It creates a mock data access object and a mock presenter to test the interactor.
     */
    @Test
    public void execute() {
        SearchRecipeDataAccessInterface dataAccess = new SearchRecipeDataAccessInterface() {
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
                        new ArrayList<>(), null, new ArrayList<>()));
                return res;
            }
        };
        SearchRecipeOutputBoundary presenter = new SearchRecipeOutputBoundary() {
            @Override
            public void prepareSuccessView(List<SearchRecipeOutputData> message) {
                List<SearchRecipeOutputData> expectedOutputData = new ArrayList<>();
                expectedOutputData.add(new CommonSearchRecipeOutputData("recipeName", "imageUrl", 1));

                assertEquals(expectedOutputData, message);
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Failed to get recipes.", message);
            }
        };
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dataAccess, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData() {
            @Override
            public String searchQuery() {
                // Notice that they don't have to be perfectly comma seperated so long as there are commas.
                return "chicken,  garlic,spinach";
            }
        };
        interactor.execute(inputData);
    }
}