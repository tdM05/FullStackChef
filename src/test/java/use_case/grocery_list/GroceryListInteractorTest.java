package use_case.grocery_list;

import data_access.grocery_list.GroceryListDataAccessObject;
import data_access.grocery_list.GroceryListException;
import data_access.grocery_list.GroceryListInMemoryDataAccessObject;
import entity.CommonIngredient;
import entity.Ingredient;
import entity.grocery_list.CommonIngredientWithConvertedUnits;
import entity.grocery_list.IngredientWithConvertedUnits;
import interface_adapter.ViewManagerModel;
import interface_adapter.grocery_list.GroceryListPresenter;
import interface_adapter.grocery_list.GroceryListViewModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import use_case.grocery_list.GroceryListInteractor;

import static org.junit.Assert.*;

public class GroceryListInteractorTest {
    // setup real database interactor, view model, and view presenter
    GroceryListDataAccessInterface dao = new GroceryListDataAccessObject();
    GroceryListViewModel viewModel = new GroceryListViewModel();
    ViewManagerModel viewManagerModel = new ViewManagerModel();
    GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel);
    GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);

    @Test
    public void executeWithFailView() {
        // Setup custom interactor
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        ingredients.add(i1);
        GroceryListDataAccessInterface dao = new GroceryListDataAccessInterface() {
            @Override
            public List<IngredientWithConvertedUnits> getAllIngredients(List<Integer> ids) {
                return List.of();
            }

            @Override
            public List<Integer> getAllRecipeIds() {
                throw new GroceryListException("Failed to get recipe ids");
            }
        };

        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 2.0 grams");
            }
            @Override
            public void prepareFailView(String error) {
                assertEquals(error, "Failed to get recipe ids");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }

    @Test
    public void executeWithConvertedIngredients() {
        // Setup custom interactor
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        ingredients.add(i1);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 2.0 grams");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }
    @Test
    public void executeWithConvertedDuplicateConvertableIngredients() {
        // Setup custom interactor
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        IngredientWithConvertedUnits i2 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        ingredients.add(i1);
        ingredients.add(i2);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 4.0 grams");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }


    @Test
    public void executeWithUnconvertableIngredients() {
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        // This ingredient cannot be converted to grams
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        ingredients.add(i1);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 1.0 random sourceUnit");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }

    @Test
    public void executeWithDuplicateUnconvertableIngredients() {
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        // This ingredient cannot be converted to grams
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        IngredientWithConvertedUnits i2 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        ingredients.add(i1);
        ingredients.add(i2);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 2.0 random sourceUnit");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }

    /**
     * This one tests a longer list that expects to output 2 ingredients instead of 1
     */
    @Test
    public void executeWithDuplicateUnconvertableIngredients2() {
        List<IngredientWithConvertedUnits> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        // This ingredient cannot be converted to grams
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        IngredientWithConvertedUnits i2 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        IngredientWithConvertedUnits i3 = new CommonIngredientWithConvertedUnits(
                "name2", 1f, 1,
                "random sourceUnit", null,
                0f, false
        );
        ingredients.add(i1);
        ingredients.add(i2);
        ingredients.add(i3);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewModel, viewManagerModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 2.0 random sourceUnit");
                assertEquals(outputData.getGroceryList().get(1), "name2 - 1.0 random sourceUnit");
                assertEquals(outputData.getGroceryList().size(), 2);
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.execute();
    }

    @Test
    public void ingredientsToStrings() {
        // Test the ingredientsToStrings method.
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        IngredientWithConvertedUnits i2 = new CommonIngredientWithConvertedUnits(
                "name2", 1f, 1,
                "random sourceUnit", "grams",
                2f, true
        );
        List<IngredientWithConvertedUnits> input = List.of(i1, i2);
        List<String> res = interactor.ingredientsToStrings(input);
        assertEquals(res.get(0), "name1 - 2.0 grams");
        assertEquals(res.get(1), "name2 - 2.0 grams");
    }

    @Test
    public void simplifiedIngredientsWithDuplicates() {
        // Test the simplifiedIngredients with ingredients that have duplicates.
        IngredientWithConvertedUnits i1 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                1f, true
                );
        IngredientWithConvertedUnits i2 = new CommonIngredientWithConvertedUnits(
                "name1", 1f, 1,
                "random sourceUnit", "grams",
                1f, true
        );
        IngredientWithConvertedUnits i3 = new CommonIngredientWithConvertedUnits(
                "name2", 1f, 1,
                "random sourceUnit", "grams",
                1f, true
        );
        List<IngredientWithConvertedUnits> input = List.of(i1, i2, i3);
        List<Ingredient> res = interactor.simplifiedIngredients(input);
        // We expect i1 and i2 to be combined with value 4 grams.
        assertEquals(res.size(), 2);
    }
}