package use_case.mealplan.update_meals;

import entity.User;
import interface_adapter.mealplan.update_meals.UpdateMealsPresenter;
import use_case.mealplan.generate_mealplan.GenerateMealPlanRecipeDto;

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
        Map<String, List<GenerateMealPlanRecipeDto>> userRecipes = dataAccess.getMeals(user.getName(), user.getPassword());

        // then we convert from Map<String, List<GenerateMealPlanRecipeDto>> to Map<LocalDate, List<GenerateMealPlanRecipeDto>>
        final UpdateMealsOutputData outputData = convertToOutputData(userRecipes);
        presenter.prepareSuccessView(outputData);
    }

    /**
     * Converts from Map<String, List<GenerateMealPlanRecipeDto>> to Map<LocalDate, List<GenerateMealPlanRecipeDto>>.
     * Then it returns in with type OutputData
     * @param userRecipes
     * @return
     */
    private UpdateMealsOutputData convertToOutputData(Map<String, List<GenerateMealPlanRecipeDto>> userRecipes) {
        // we need to first convert to  Map<LocalDate, List<GenerateMealPlanRecipeDto>>
        // Get the current week's Monday
        LocalDate currentMonday = LocalDate.now().with(DayOfWeek.MONDAY);

        // Create the new map
        Map<LocalDate, List<GenerateMealPlanRecipeDto>> dateKeyedMap = new HashMap<>();

        // Iterate over the input map
        for (Map.Entry<String, List<GenerateMealPlanRecipeDto>> entry : userRecipes.entrySet()) {
            String dayName = entry.getKey().toLowerCase(Locale.ROOT);
            List<GenerateMealPlanRecipeDto> recipes = entry.getValue();

            // Convert the day name to DayOfWeek
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayName.toUpperCase(Locale.ROOT));

            // Calculate the corresponding LocalDate
            LocalDate date = currentMonday.with(dayOfWeek);

            // Add to the new map
            dateKeyedMap.put(date, recipes);
        }
        // then we return it with type OutputData
        return new UpdateMealsOutputData(dateKeyedMap);

    }
}
