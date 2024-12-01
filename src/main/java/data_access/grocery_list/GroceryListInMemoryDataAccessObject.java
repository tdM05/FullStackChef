package data_access.grocery_list;

import entity.CommonIngredientWithConvertedUnits;
import entity.Ingredient;
import entity.User;
import use_case.grocery_list.GroceryListDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory data access object for the GroceryList.
 */
public class GroceryListInMemoryDataAccessObject implements GroceryListDataAccessInterface {
    private List<Ingredient> ingredients;

    public GroceryListInMemoryDataAccessObject() {
        // This is default dummy data
        ingredients = new ArrayList<>();
        ingredients.add(new CommonIngredientWithConvertedUnits("name1", 1f, "grams"));
    }

    public GroceryListInMemoryDataAccessObject(List<Ingredient> ingredients) {
        // This is default dummy data
        this.ingredients = ingredients;
    }

    @Override
    public List<Integer> getAllRecipeIds(String username) {
        return new ArrayList<>();
    }


    @Override
    public List<Ingredient> getAllIngredients(List<Integer> ids) {
        return ingredients;
    }
}
