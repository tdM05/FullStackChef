package use_case.grocery_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_access.grocery_list.GroceryListException;
import entity.CommonIngredient;
import entity.Ingredient;
import entity.grocery_list.IngredientWithConvertedUnits;

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
            final List<Integer> recipeIds = dataAccess.getAllRecipeIds();
            // Get ingredients from the recipe ids
            final List<IngredientWithConvertedUnits> ingredients = dataAccess.getAllIngredients(recipeIds);
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
    List<String> ingredientsToStrings(List<IngredientWithConvertedUnits> ingredients) {
        // We first need to simplify the ingredients and add duplicate names.
        final List<Ingredient> simplifiedIngredients = simplifiedIngredients(ingredients);

        final List<String> res = new java.util.ArrayList<>();
        for (Ingredient ingredient : simplifiedIngredients) {
            final String name = ingredient.getName();
            final float value = ingredient.getAmount();
            final String unit = ingredient.getUnit();
            res.add(name + " - " + value + " " + unit);
        }
        return res;
    }

    /**
     * Simplify the ingredients by adding the amounts of ingredients with the same name.
     *
     * @param ingredients The list of ingredients.
     * @return The simplified list of ingredients.
     */
    List<Ingredient> simplifiedIngredients(List<IngredientWithConvertedUnits> ingredients) {
        // Get ingredients with the same name and simplify
        // We do this by creating a new list, and seing if a name is already in the list
        final List<Ingredient> res = new ArrayList<>();
        for (IngredientWithConvertedUnits ingredient : ingredients) {
            // we need to check if this ingredient was converted successully
            if (!ingredient.getConvertStatus()) {
                boolean found = false;
                for (Ingredient resIngredient : res) {
                    if (resIngredient.getName().equals(ingredient.getName())) {
                        // Here we assume the units are the same, which they should be
                        resIngredient.setAmount(resIngredient.getAmount() + ingredient.getAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // make sure we are returning the converted amount
                    final Ingredient newIngredient = new CommonIngredient(ingredient.getName(),
                            ingredient.getAmount(),
                            ingredient.getUnit());
                    res.add(newIngredient);
                }
            }
            else {
                boolean found = false;
                for (Ingredient resIngredient : res) {
                    if (resIngredient.getName().equals(ingredient.getName())) {
                        // Here we assume the units are the same, which they should be
                        resIngredient.setAmount(resIngredient.getAmount() + ingredient.getConvertedAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // make sure we are returning the converted amount
                    final Ingredient newIngredient = new CommonIngredient(ingredient.getName(),
                            ingredient.getConvertedAmount(),
                            ingredient.getConvertedUnit());
                    res.add(newIngredient);
                }
            }
        }
        return res;
    }
}
