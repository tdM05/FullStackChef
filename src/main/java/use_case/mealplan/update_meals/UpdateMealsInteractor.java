package use_case.mealplan.update_meals;

import entity.User;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpdateMealsInteractor implements UpdateMealsInputBoundary {
    private final UpdateMealsDataAccessInterface dataAccess;
    private final UpdateMealsOutputBoundary presenter;
    public UpdateMealsInteractor(
            UpdateMealsOutputBoundary presenter,
            UpdateMealsDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }
    @Override
    public void execute(User user) {
        // we need to first get all the recipes ids from user api
        Map<String, List<WeeklyMealRecipeDto>> userRecipes = dataAccess.getMeals(user.getName(), user.getPassword());

        // then we convert from Map<String, List<GenerateMealPlanRecipeDto>> to Map<LocalDate, List<GenerateMealPlanRecipeDto>>
        final UpdateMealsOutputData outputData = convertToOutputData(userRecipes);
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Converts from Map<String, List<WeeklyMealRecipeDto>> to Map<LocalDate, List<GenerateMealPlanRecipeDto>>.
     * Then it returns in with type OutputData
     * @param userRecipes
     * @return
     */
    private UpdateMealsOutputData convertToOutputData(Map<String, List<WeeklyMealRecipeDto>> userRecipes) {
// Get the current day's date (start point for keys)
        LocalDate day = LocalDate.now();

// Create the new map
        Map<LocalDate, List<WeeklyMealRecipeDto>> dateKeyedMap = new HashMap<>();

// Iterate over the input map
        for (Map.Entry<String, List<WeeklyMealRecipeDto>> entry : userRecipes.entrySet()) {
            List<WeeklyMealRecipeDto> recipes = entry.getValue();

            // Add the entry to the map using the current 'day' as the key
            dateKeyedMap.put(day, recipes);

            // Increment 'day' by one
            day = day.plusDays(1);
        }

// Return the result wrapped in the output data type
        return new UpdateMealsOutputData(dateKeyedMap);

    }
}
