package use_case.grocery_list;

import app.SessionUser;
import data_access.grocery_list.GroceryListDataAccessObject;
import data_access.grocery_list.GroceryListException;
import data_access.grocery_list.GroceryListInMemoryDataAccessObject;
import entity.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.grocery_list.GroceryListPresenter;
import interface_adapter.grocery_list.GroceryListViewModel;
import interface_adapter.search.SearchViewModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import app.SessionUser;
import static org.junit.Assert.*;
import entity.CommonUser;

public class GroceryListInteractorTest {
    // setup real database interactor, view model, and view presenter
    ViewManagerModel viewManagerModel = new ViewManagerModel();
    SearchViewModel searchViewModel = new SearchViewModel();
    GroceryListViewModel viewModel = new GroceryListViewModel();


    @Test
    public void execute1() {

        List<Ingredient> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        Ingredient i1 = new CommonIngredient(
                "name1", 1f, 1, "g"
        );
        Ingredient i2 = new CommonIngredient(
                "name2", 1f, 1, "g"
        );
        ingredients.add(i1);
        ingredients.add(i2);
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject(ingredients);
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewManagerModel, searchViewModel, viewModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
                assertEquals(outputData.getGroceryList().get(0), "name1 - 1.0 g");
                assertEquals(outputData.getGroceryList().get(1), "name2 - 1.0 g");
            }

            @Override
            public void prepareFailView(String error) {
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);

        //login
        User user = new CommonUser("username", "password");
        // log a user in
        SessionUser session = SessionUser.getInstance();
        session.setUser(user);
        interactor.execute();
    }

    @Test
    public void exceptionTest() {
        List<Ingredient> ingredients = new ArrayList<>();
        // the dao getAllIngredients will return a list with this ingredient
        Ingredient i1 = new CommonIngredient(
                "name1", 1f, 1, "g"
        );
        Ingredient i2 = new CommonIngredient(
                "name2", 1f, 1, "g"
        );
        ingredients.add(i1);
        ingredients.add(i2);
        GroceryListDataAccessInterface dao = new GroceryListDataAccessInterface() {
            @Override
            public List<Ingredient> getAllIngredients(List<Integer> ids) {
                throw new GroceryListException("error");
            }

            @Override
            public List<Integer> getAllRecipeIds(String username) {
                return List.of();
            }
        };
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewManagerModel, searchViewModel, viewModel) {
            @Override
            public void prepareSuccessView(GroceryListOutputData outputData) {
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals(error, "error");
            }
        };
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);

        //login
        User user = new CommonUser("username", "password");
        // log a user in
        SessionUser session = SessionUser.getInstance();
        session.setUser(user);
        interactor.execute();
    }

    @Test
    public void switchToSearchView() {
        GroceryListDataAccessInterface dao = new GroceryListInMemoryDataAccessObject();
        GroceryListOutputBoundary presenter = new GroceryListPresenter(viewManagerModel, searchViewModel, viewModel);
        GroceryListInteractor interactor = new GroceryListInteractor(dao, presenter);
        interactor.switchToSearchView();
        assertEquals(viewManagerModel.getState(), "searchView");
    }
}