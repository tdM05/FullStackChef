package use_case.mealplan.generate_mealplan;

import entity.Recipe;
import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Interactor for the Generate Meal Plan Use Case.
 *
 * This class handles the core business logic for generating a meal plan.
 * It fetches the meal plan data from the data access layer, processes the data,
 * and prepares it for presentation.
 */
public class GenerateMealPlanInteractor implements GenerateMealPlanInputBoundary {

    private final GenerateMealPlanOutputBoundary mealPlanPresenter;
    private final GenerateMealPlanDataAccessInterface mealPlanDataAccessObject;

    /**
     * Constructs a GenerateMealPlanInteractor.
     *
     * @param presenter  The output boundary to prepare views for the use case.
     * @param dataAccess The data access interface to interact with external data sources.
     */
    public GenerateMealPlanInteractor(GenerateMealPlanOutputBoundary presenter, GenerateMealPlanDataAccessInterface dataAccess) {
        mealPlanPresenter = presenter;
        mealPlanDataAccessObject = dataAccess;
    }

    /**
     * Executes the meal plan generation use case.
     *
     * This method fetches the weekly meal plan from the data access layer,
     * converts the day keys into LocalDate values, transforms the data into DTOs,
     * and passes the processed data to the presenter for further handling.
     *
     * @param inputData The input data containing the diet and start date.
     */
    @Override
    public void execute(GenerateMealPlanInputData inputData) {
        try {
            // Fetch the meal plan
            Map<String, List<Recipe>> rawMealPlan = mealPlanDataAccessObject.generateWeeklyMealPlan(
                    inputData.getDiet(),
                    inputData.getStartDate()
            );

            // Convert string keys to LocalDate and transform recipes to DTOs
            Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = convertKeysAndTransformRecipes(rawMealPlan, LocalDate.parse(inputData.getStartDate()));

            // Prepare success output
            mealPlanPresenter.prepareSuccessView(new GenerateMealPlanOutputData(mealPlan));

        } catch (IOException | JSONException e) {
            // Notify the presenter of failure with the error message
            mealPlanPresenter.prepareFailView("Error: " + e.getMessage());
        }
    }

    /**
     * Converts the string keys in the raw meal plan to LocalDate values
     * and transforms recipes into GenerateMealPlanRecipeDto objects.
     *
     * The start date is used as the base date, and subsequent days are calculated
     * by incrementing the date by the appropriate number of days.
     *
     * @param rawMealPlan The raw meal plan data with string day keys (e.g., "Monday").
     * @param startDate   The start date for the meal plan.
     * @return A map where the keys are LocalDate values and the values are lists of GenerateMealPlanRecipeDto objects.
     */
    private Map<LocalDate, List<GenerateMealPlanRecipeDto>> convertKeysAndTransformRecipes(Map<String, List<Recipe>> rawMealPlan, LocalDate startDate) {
        Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = new HashMap<>();
        int dayIndex = 0;

        // Iterate over the raw meal plan entries and map them to LocalDate keys
        for (List<Recipe> recipes : rawMealPlan.values()) {
            LocalDate date = startDate.plusDays(dayIndex);
            List<GenerateMealPlanRecipeDto> dtoList = recipes.stream()
                    .map(recipe -> new GenerateMealPlanRecipeDto(recipe.getRecipeId(), recipe.getTitle())) // Convert Recipe to DTO
                    .collect(Collectors.toList());
            mealPlan.put(date, dtoList);
            dayIndex++;
        }

        return mealPlan;
    }
}
