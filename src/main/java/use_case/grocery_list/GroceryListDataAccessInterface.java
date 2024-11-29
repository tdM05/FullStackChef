package use_case.grocery_list;

import java.util.List;

import entity.grocery_list.IngredientWithConvertedUnits;

/**
 * Interface for the GroceryList data access.
 */
public interface GroceryListDataAccessInterface {
    /**
     * Get all ingredients from a list of recipe ids.
     *
     * @param ids The list of recipe IDs.
     * @return The list of ingredients.
     */
    List<IngredientWithConvertedUnits> getAllIngredientsWithConvertedUnits(List<Integer> ids);


    /**
     * Get all recipe IDs for this week.
     *
     * @return The list of recipe IDs.
     */
    List<Integer> getAllRecipeIds();
}
