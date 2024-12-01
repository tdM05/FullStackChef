package use_case.mealplan;

import entity.CommonRecipe;
import entity.CommonUser;
import entity.Recipe;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.mealplan.generate_mealplan.WeeklyMealPresenter;
import interface_adapter.mealplan.generate_mealplan.WeeklyMealViewModel;
import org.junit.Before;
import org.junit.Test;
import use_case.mealplan.generate_mealplan.*;
import use_case.set_meals.StoreMealInputBoundary;
import use_case.set_meals.StoreMealInputData;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class WeeklyMealInteractorTest {

    private CommonUser testUser;

    @Before
    public void setup() {
        testUser = new CommonUser("giselle", "chromehearts"); // Initialize testUser
    }

    @Test
    public void testExecuteWithValidData() {
        // Mock DAO
        WeeklyMealDataAccessInterface dao = new WeeklyMealDataAccessInterface() {
            @Override
            public Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String startDate) {
                // Provide mock data
                Map<String, List<Recipe>> mockData = new HashMap<>();

                List<Recipe> mondayRecipes = List.of(
                        new CommonRecipe(1100990, "Blueberry, Chocolate & Cocao Superfood Pancakes - Gluten-Free/Paleo/Vegan", null, null, null, null, null, false),
                        new CommonRecipe(1095794, "Spanish Tortilla", null, null, null, null, null, false),
                        new CommonRecipe(641145, "Curry-Braised Chicken", null, null, null, null, null, false)
                );
                mockData.put("Monday", mondayRecipes);

                List<Recipe> tuesdayRecipes = List.of(
                        new CommonRecipe(648006, "Irish Soda Bread By Mommie Cooks", null, null, null, null, null, false),
                        new CommonRecipe(664565, "Vegetable Minestrone Soup", null, null, null, null, null, false),
                        new CommonRecipe(649718, "Lemon Pasta Alfredo (Vegan)", null, null, null, null, null, false)
                );
                mockData.put("Tuesday", tuesdayRecipes);

                return mockData;
            }
        };

        // Mock ViewManagerModel and WeeklyMealViewModel
        ViewManagerModel viewManager = new ViewManagerModel();
        WeeklyMealViewModel viewModel = new WeeklyMealViewModel();

        // Mock Presenter
        WeeklyMealOutputBoundary presenter = new WeeklyMealPresenter(viewManager, viewModel) {
            @Override
            public void prepareSuccessView(WeeklyMealOutputData outputData) {
                // Validate the mock data in the output
                assertEquals(3, outputData.getMealPlan().get(LocalDate.parse("2024-11-25")).size()); // Monday
                assertEquals(3, outputData.getMealPlan().get(LocalDate.parse("2024-11-26")).size()); // Tuesday
            }

            @Override
            public void prepareFailView(String error) {
                fail("The test failed with an error: " + error);
            }
        };

        // Mock StoreMealInputBoundary
        StoreMealInputBoundary storeMealUseCase = new StoreMealInputBoundary() {
            @Override
            public void execute(StoreMealInputData inputData, User user) {
                // Validate the meal IDs input
                Map<String, List<Integer>> mealIds = inputData.getMealIds();
                assertNotNull(mealIds);
                assertEquals(3, mealIds.get("Monday").size());
                assertEquals(3, mealIds.get("Tuesday").size());
                assertEquals(Integer.valueOf(1100990), mealIds.get("Monday").get(0)); // Check first Monday recipe ID
                assertEquals(Integer.valueOf(664565), mealIds.get("Tuesday").get(1)); // Check second Tuesday recipe ID
            }
        };

        WeeklyMealInteractor interactor = new WeeklyMealInteractor(presenter, dao, storeMealUseCase);
        WeeklyMealInputData inputData = new WeeklyMealInputData("vegan", "2024-11-25");
        interactor.execute(inputData, testUser);
    }

    @Test
    public void testExecuteWithException() {
        // Mock DAO that throws an IOException
        WeeklyMealDataAccessInterface dao = new WeeklyMealDataAccessInterface() {
            @Override
            public Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String startDate) throws IOException {
                throw new IOException("Mock IOException");
            }
        };

        // Mock Presenter
        WeeklyMealOutputBoundary presenter = new WeeklyMealPresenter(new ViewManagerModel(), new WeeklyMealViewModel()) {
            @Override
            public void prepareFailView(String error) {
                // Validate the error message passed to the presenter
                assertEquals("Error: Mock IOException", error);
            }
        };

        StoreMealInputBoundary storeMealUseCase = new StoreMealInputBoundary() {
            @Override
            public void execute(StoreMealInputData inputData, User user) {
                fail("StoreMealInputBoundary should not be called on failure.");
            }
        };

        WeeklyMealInteractor interactor = new WeeklyMealInteractor(presenter, dao, storeMealUseCase);
        WeeklyMealInputData inputData = new WeeklyMealInputData("vegan", "2024-11-25");
        CommonUser mockUser = new CommonUser("testUser", "testPassword");
        interactor.execute(inputData, mockUser);
    }


    @Test
    public void testSwitchToSearchView() {
        // Mock ViewManagerModel and Presenter
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        WeeklyMealViewModel viewModel = new WeeklyMealViewModel();
        WeeklyMealPresenter presenter = new WeeklyMealPresenter(viewManagerModel, viewModel);

        // Mock dataAccess and storeMealUseCase
        WeeklyMealDataAccessInterface dataAccess = new WeeklyMealDataAccessInterface() {
            @Override
            public Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String startDate) {
                return new HashMap<>();
            }
        };

        StoreMealInputBoundary storeMealUseCase = new StoreMealInputBoundary() {
            @Override
            public void execute(StoreMealInputData inputData, User user) {
                // Mock implementation for storeMealUseCase
            }
        };

        WeeklyMealInteractor interactor = new WeeklyMealInteractor(presenter, dataAccess, storeMealUseCase);
        interactor.switchToSearchView();
        assertEquals("searchView", viewManagerModel.getState());
    }
}
