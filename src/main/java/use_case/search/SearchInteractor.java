package use_case.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import entity.Recipe;
/**
 * The interactor for the Search Use Case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface dataAccess;
    private final SearchOutputBoundary presenter;

    public SearchInteractor(SearchDataAccessInterface dataAccess, SearchOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * This executes the search for recipes that match the given ingredients.
     *
     * @param searchInputData the ingredients to search for
     */
    @Override
    public void execute(SearchInputData searchInputData) {

        final String searchString = searchInputData.getQuery();

        final List<String> recipeList = splitString(searchString);
        try {
            final List<Recipe> recipes = dataAccess.getRecipes(recipeList,6);

            final List<SearchOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
            presenter.prepareSuccessView(outputData);
        }
        catch (IOException | JSONException ex) {
            presenter.prepareFailView("Failed to get recipes.");
        }
    }

    private List<SearchOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<SearchOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new SearchOutputData(recipe.getRecipeId(),recipe.getTitle(),recipe.getImage()));
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
