package data_access.grocery_list;

import entity.Measurable;
import entity.Pair;
import entity.grocery_list.IngredientWithConvertedUnits;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroceryListDataAccessObjectTest {

    @Test
    public void getAllIngredientsWithOneID() {
        List<Integer> input = new ArrayList<>();
        input.add(716429);
        GroceryListDataAccessObject groceryListDataAccessObject = new GroceryListDataAccessObject();

        List<IngredientWithConvertedUnits> res = groceryListDataAccessObject.getAllIngredients(input);
        assert res.get(0).getName().equals("butter");
    }

    @Test
    public void convertToStandardUnitsSuccess() {
        GroceryListDataAccessObject groceryListDataAccessObject = new GroceryListDataAccessObject();
        Pair<Measurable<Float>, Boolean> res = groceryListDataAccessObject.convertToStandardUnits("garlic", 2, "cloves");
        Measurable<Float> measurablePart = res.getFirst();
        boolean status = res.getSecond();
        assertEquals(measurablePart.getNumber(), 10, 0.1);
        assertEquals(measurablePart.getUnit(), "grams");
        assertEquals(status, true);
    }

    @Test
    public void convertToStandardUnitsFailure() {
        // This is weird because the api still works when giving it nonsense.
        GroceryListDataAccessObject groceryListDataAccessObject = new GroceryListDataAccessObject();
        Pair<Measurable<Float>, Boolean> res = groceryListDataAccessObject.convertToStandardUnits("asdfasdfasfdsaf", 2, "asdasdasdasdasd");
        Measurable<Float> measurablePart = res.getFirst();
        boolean status = res.getSecond();
        assertEquals(status, true);
    }
}