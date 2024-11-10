package use_case.search_recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import entity.Recipe;

/**
 * The interactor for the Search Recipe Use Case.
 */
public class SearchRecipeInteractor implements SearchRecipeInputBoundary {
    private final SearchRecipeDataAccessInterface recipeDataAccessObject;
    private final SearchRecipeOutputBoundary recipePresenter;

    public SearchRecipeInteractor(SearchRecipeDataAccessInterface recipeDataAccessObject,
                                  SearchRecipeOutputBoundary recipePresenter
                                  ) {
        this.recipeDataAccessObject = recipeDataAccessObject;
        this.recipePresenter = recipePresenter;
    }

    /**
     * This executes the search for recipes that match the given ingredients.
     *
     * @param searchRecipeInputData the ingredients to search for
     */
    @Override
    public void execute(SearchRecipeInputData searchRecipeInputData) {
        // We are expecting searchRecipeInputData to be whatever the user typed
        // This should be a comma seperated list of ingredients
        final String searchString = searchRecipeInputData.searchQuery();

        // We will get the list of recipes from the data access object
        final List<String> recipeList = splitString(searchString);
        try {
            final List<Recipe> recipes = recipeDataAccessObject.getRecipes(recipeList, 6);
            // Then we convert this into output data
            final List<SearchRecipeOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
            recipePresenter.prepareSuccessView(outputData);

        }
        catch (IOException | JSONException ex) {
            recipePresenter.prepareFailView("Failed to get recipes.");
        }
    }

    private List<SearchRecipeOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<SearchRecipeOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new CommonSearchRecipeOutputData(recipe.getTitle(), recipe.getImage(), recipe.getId()));
        }
        return outputData;
    }

    /**
     * Splits the search string into a list of ingredients and removes any whitespace.
     * ex: "a,   b, d" -> ["a", "b", "d"]
     * @param searchString the search string
     * @return the list of ingredients
     */
    private List<String> splitString(String searchString) {
        final String[] parts = searchString.split(",");
        final List<String> result = new ArrayList<>();
        for (String part : parts) {
            result.add(part.trim());
        }

        return result;
    }
}
