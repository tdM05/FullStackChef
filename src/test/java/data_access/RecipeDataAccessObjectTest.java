package data_access;

import entity.CommonIngredient;
import entity.Ingredient;
import entity.Recipe;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeDataAccessObjectTest {

    /**
     * This test case tests the getRecipes method in the RecipeDataAccessObject class.
     * @throws IOException if an I/O or JSON parsing error occurs.
     */
    @Test
    public void getRecipes1() throws IOException {
        // First create a data access object
        RecipeDataAccessObject dao = new RecipeDataAccessObject();

        // This is an ingredient
        String i1 = "apple";

        // Convert this ingredient to a list
        final List<String> ingredients = new ArrayList<>(Arrays.asList(i1));
        // Note that if you specify too many elements in the list, you may get an empty response since
        // there are no recipes with all the ingredients specified.
        List<Recipe> recipes = dao.getRecipes(ingredients, 5);
        assertNotNull(recipes);
        // put a breakpoint on the assert line so that you can see what the response is (which is stored in recipes).
    }

    /**
     * This test case is similar to the previous one, but it has two ingredients.
     * @throws IOException if an I/O or JSON parsing error occurs.
     */
    @Test
    public void getRecipes2() throws IOException {
        // First create a data access object
        RecipeDataAccessObject dao = new RecipeDataAccessObject();

        // This is an ingredient
        String i1 = "apple";
        String i2 = "banana";

        // Convert this ingredient to a list
        final List<String> ingredients = new ArrayList<>(Arrays.asList(i1, i2));
        List<Recipe> recipes = dao.getRecipes(ingredients, 5);
        assertNotNull(recipes);
        // put a breakpoint on the assert line so that you can see what the response is (which is stored in recipes).
    }
    /**
     * This test case for a typo in the ingredient
     * @throws IOException if an I/O or JSON parsing error occurs.
     */
    @Test
    public void getRecipesNull() throws IOException {
        // First create a data access object
        RecipeDataAccessObject dao = new RecipeDataAccessObject();

        // This is an ingredient
        String i1 = "asdfasdfas";

        // Convert this ingredient to a list
        final List<String> ingredients = new ArrayList<>(Arrays.asList(i1));
        // Note that if you specify too many elements in the list, you may get a null response since
        // there are no recipes with all the ingredients specified.
        List<Recipe> recipes = dao.getRecipes(ingredients, 5);
        assertEquals(recipes.size(), 0);
        // put a breakpoint on the assert line so that you can see what the response is (which is stored in recipes).
    }
}