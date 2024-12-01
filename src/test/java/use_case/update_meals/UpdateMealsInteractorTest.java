package use_case.update_meals;

import entity.CommonUser;
import entity.User;
import org.junit.Test;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;
import use_case.mealplan.update_meals.UpdateMealsDataAccessInterface;
import use_case.mealplan.update_meals.UpdateMealsInteractor;
import use_case.mealplan.update_meals.UpdateMealsOutputBoundary;
import use_case.mealplan.update_meals.UpdateMealsOutputData;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UpdateMealsInteractorTest {

    @Test
    public void testExecuteWithValidData() {
        // Mock Data Access
        UpdateMealsDataAccessInterface dataAccess = new UpdateMealsDataAccessInterface() {
            @Override
            public Map<String, List<WeeklyMealRecipeDto>> getMeals(String username, String password) {
                // Provide mock data for meals
                Map<String, List<WeeklyMealRecipeDto>> mockData = new HashMap<>();
                mockData.put("Monday", List.of(
                        new WeeklyMealRecipeDto(716276, "Doughnuts"),
                        new WeeklyMealRecipeDto(642585, "Farfalle with fresh tomatoes, basil and mozzarella"),
                        new WeeklyMealRecipeDto(657956, "Ravioli With Arugula, Pine Nuts, Raisins & Cream")
                ));
                mockData.put("Tuesday", List.of(
                        new WeeklyMealRecipeDto(716276, "Doughnuts"),
                        new WeeklyMealRecipeDto(643634, "Macaroni with Fresh Tomatoes and Beans"),
                        new WeeklyMealRecipeDto(661094, "Spicy Eggplant Spaghetti")
                ));
                return mockData;
            }
        };

        // Mock Presenter
        UpdateMealsOutputBoundary presenter = new UpdateMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateMealsOutputData outputData) {
                // Validate the output data
                Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan = outputData.getMealPlan();

                // Validate Monday's meals
                assertTrue(mealPlan.containsKey(LocalDate.now()));
                List<WeeklyMealRecipeDto> mondayMeals = mealPlan.get(LocalDate.now());
                assertEquals(3, mondayMeals.size());
                assertEquals("Doughnuts", mondayMeals.get(0).getTitle());
                assertEquals("Farfalle with fresh tomatoes, basil and mozzarella", mondayMeals.get(1).getTitle());
                assertEquals("Ravioli With Arugula, Pine Nuts, Raisins & Cream", mondayMeals.get(2).getTitle());

                // Validate Tuesday's meals
                assertTrue(mealPlan.containsKey(LocalDate.now().plusDays(1)));
                List<WeeklyMealRecipeDto> tuesdayMeals = mealPlan.get(LocalDate.now().plusDays(1));
                assertEquals(3, tuesdayMeals.size());
                assertEquals("Doughnuts", tuesdayMeals.get(0).getTitle());
                assertEquals("Macaroni with Fresh Tomatoes and Beans", tuesdayMeals.get(1).getTitle());
                assertEquals("Spicy Eggplant Spaghetti", tuesdayMeals.get(2).getTitle());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Failure should not occur for this test: " + error);
            }
        };

        // Create Interactor
        UpdateMealsInteractor interactor = new UpdateMealsInteractor(presenter, dataAccess);

        // Mock User
        User mockUser = new CommonUser("testUser", "testPassword");

        // Execute Use Case
        interactor.execute(mockUser);
    }


    @Test
    public void testExecuteWithNoData() {
        // Mock Data Access returning empty data
        UpdateMealsDataAccessInterface dataAccess = new UpdateMealsDataAccessInterface() {
            @Override
            public Map<String, List<WeeklyMealRecipeDto>> getMeals(String username, String password) {
                return new HashMap<>(); // No meals data
            }
        };

        // Mock Presenter
        UpdateMealsOutputBoundary presenter = new UpdateMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateMealsOutputData outputData) {
                // Validate empty data handling
                assertTrue(outputData.getMealPlan().isEmpty());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Failure should not occur for this test: " + error);
            }
        };

        // Create Interactor
        UpdateMealsInteractor interactor = new UpdateMealsInteractor(presenter, dataAccess);

        // Mock User
        User mockUser = new CommonUser("testUser", "testPassword");

        // Execute Use Case
        interactor.execute(mockUser);
    }
}
