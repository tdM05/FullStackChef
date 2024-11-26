package data_access.grocery_list;

import entity.grocery_list.CommonIngredientWithConvertedUnits;
import entity.grocery_list.IngredientWithConvertedUnits;
import use_case.grocery_list.GroceryListDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory data access object for the GroceryList.
 */
public class GroceryListInMemoryDataAccessObject implements GroceryListDataAccessInterface {
    private List<IngredientWithConvertedUnits> ingredients;

    public GroceryListInMemoryDataAccessObject() {
        // This is default dummy data
        ingredients = new ArrayList<>();
        ingredients.add(new CommonIngredientWithConvertedUnits("name1", 1f, "grams"));
    }

    public GroceryListInMemoryDataAccessObject(List<IngredientWithConvertedUnits> ingredients) {
        // This is default dummy data
        this.ingredients = ingredients;
    }

    @Override
    public List<Integer> getAllRecipeIds() {
        return new ArrayList<>();
    }

    @Override
    public List<IngredientWithConvertedUnits> getAllIngredients(List<Integer> ids) {
        return ingredients;
    }
}
