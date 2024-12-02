package use_case.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Recipe;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import entity.CommonDietaryRestriction;
import org.json.JSONException;

/**
 * The interactor for the Search Use Case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface dataAccess;
    private final DietaryRestrictionDataAccessInterface dietaryDataAccess;
    private final SearchOutputBoundary presenter;

    /**
     * Constructs a SearchInteractor with the specified data access, dietary data access, and presenter.
     *
     * @param dataAccess          the data access interface for searching recipes
     * @param dietaryDataAccess   the data access interface for dietary restrictions
     * @param presenter           the presenter to output results
     */
    public SearchInteractor(SearchDataAccessInterface dataAccess,
                            DietaryRestrictionDataAccessInterface dietaryDataAccess,
                            SearchOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.dietaryDataAccess = dietaryDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the search for recipes that match the given ingredients and dietary restrictions.
     *
     * @param searchInputData the ingredients to search for
     */
    @Override
    public void execute(SearchInputData searchInputData) {
        final String searchString = searchInputData.getQuery();
        final List<String> ingredientList = splitString(searchString);
        int numberOfResults = 6; // You can make this configurable if needed

        try {
            // Fetch the current user's dietary restrictions
            CommonDietaryRestriction dietaryRestrictions = dietaryDataAccess.loadDietaryRestrictions();
            List<String> dietaryFilters = dietaryRestrictions.getDiets();

            // Debug statement to check dietary filters
            System.out.println("Dietary Filters Retrieved in Interactor: " + dietaryFilters);

            // Fetch recipes using ingredients and dietary restrictions
            final List<Recipe> recipes = dataAccess.getRecipes(ingredientList, dietaryFilters, numberOfResults);

            final List<SearchOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
            presenter.prepareSuccessView(outputData);
        } catch (IOException | JSONException ex) {
            // Log the exception (use System.err.println)
            System.err.println("Error during recipe search: " + ex.getMessage());
            presenter.prepareFailView("Failed to get recipes.");
        }
    }

    /**
     * Converts a list of Recipe entities to a list of SearchOutputData.
     *
     * @param recipes the list of Recipe entities
     * @return the list of SearchOutputData
     */
    private List<SearchOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<SearchOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new SearchOutputData(recipe.getRecipeId(), recipe.getTitle(), recipe.getImage()));
        }
        return outputData;
    }

    /**
     * Splits the search string into a list of ingredients and removes any whitespace.
     * Example: "a,   b, d" -> ["a", "b", "d"]
     *
     * @param searchString the search string
     * @return the list of ingredients
     */
    private List<String> splitString(String searchString) {
        if (searchString == null || searchString.trim().isEmpty()) {
            return new ArrayList<>();
        }

        final String[] parts = searchString.split(",");
        final List<String> result = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }
}
