package use_case.grocery_list;

import java.util.ArrayList;
import java.util.List;

import app.SessionUser;
import data_access.grocery_list.GroceryListException;
import entity.CommonIngredient;
import entity.Ingredient;
import entity.IngredientWithConvertedUnits;

/**
 * The interactor for the Grocery List use case.
 */
public class GroceryListInteractor implements GroceryListInputBoundary {

    private final GroceryListDataAccessInterface dataAccess;
    private final GroceryListOutputBoundary presenter;

    public GroceryListInteractor(GroceryListDataAccessInterface dataAccess,
                                 GroceryListOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        try {
            // Get recipe ids from the profile api
            final List<Integer> recipeIds = dataAccess.getAllRecipeIds(SessionUser.getInstance().getUser().getName());
            // Get ingredients from the recipe ids
            final List<Ingredient> ingredients = dataAccess.getAllIngredients(recipeIds);
            // Convert ingredients to strings
            final List<String> ingredientStrings = ingredientsToStrings(ingredients);

            final GroceryListOutputData outputData = new GroceryListOutputData(ingredientStrings);
            presenter.prepareSuccessView(outputData);
        }
        catch (GroceryListException ex) {
            presenter.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Convert a list of ingredients to a list of strings based on the standard unit grams
     *
     * @param ingredients The list of ingredients.
     * @return The list of strings.
     */
    List<String> ingredientsToStrings(List<Ingredient> ingredients) {
        // We first need to simplify the ingredients and add duplicate names
        final List<String> res = new java.util.ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            final String name = ingredient.getName();
            final float value = ingredient.getAmount();
            final String unit = ingredient.getUnit();
            res.add(name + " - " + value + " " + unit);
        }
        return res;
    }

    @Override
    public void switchToSearchView() {
        presenter.switchToSearchView();
    }
}
