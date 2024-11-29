package use_case.mealplan.generate_mealplan;

import data_access.DietaryRestrictionDataAccessInterface;
import data_access.dietaryrestrictions.DietaryRestrictionDataAccessObject;
import entity.DietaryRestriction;
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
    private final DietaryRestrictionDataAccessInterface dietaryAccess;

    /**
     * Constructs a GenerateMealPlanInteractor.
     *
     * @param presenter     The output boundary to prepare views for the use case.
     * @param dataAccess    The data access interface to interact with external data sources.
     * @param dietaryAccess The data access object to handle dietary restrictions.
     */
    public GenerateMealPlanInteractor(GenerateMealPlanOutputBoundary presenter,
                                      GenerateMealPlanDataAccessInterface dataAccess,
                                      DietaryRestrictionDataAccessInterface dietaryAccess) {
        this.mealPlanPresenter = presenter;
        this.mealPlanDataAccessObject = dataAccess;
        this.dietaryAccess = dietaryAccess;
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
            // Fetch dietary restrictions if dietaryAccess is provided
            String diet = null;
            if (dietaryAccess != null) {
                System.out.println("Fetching dietary restrictions...");
                DietaryRestriction dietaryRestriction = dietaryAccess.loadDietaryRestrictions();
                diet = dietaryRestriction != null && !dietaryRestriction.getDiets().isEmpty()
                        ? String.join(",", dietaryRestriction.getDiets())
                        : null;
                System.out.println("Dietary restrictions fetched: " + (diet != null ? diet : "None"));
            } else {
                System.out.println("Dietary restrictions are not set. Defaulting to no restrictions.");
            }

            // Fetch the meal plan
            System.out.println("Fetching meal plan from API...");
            Map<String, List<Recipe>> rawMealPlan = mealPlanDataAccessObject.generateWeeklyMealPlan(diet, inputData.getStartDate());
            System.out.println("Meal plan fetched successfully.");

            // Convert string keys to LocalDate and transform recipes to DTOs
            Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = convertKeysAndTransformRecipes(
                    rawMealPlan, LocalDate.parse(inputData.getStartDate())
            );

            // Prepare success output
            mealPlanPresenter.prepareSuccessView(new GenerateMealPlanOutputData(mealPlan));

        } catch (IOException e) {
            mealPlanPresenter.prepareFailView("Failed to fetch meal plan: " + e.getMessage());
        } catch (JSONException e) {
            mealPlanPresenter.prepareFailView("Failed to parse meal plan data: " + e.getMessage());
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
        if (rawMealPlan == null || rawMealPlan.isEmpty()) {
            return new HashMap<>();
        }

        Map<LocalDate, List<GenerateMealPlanRecipeDto>> mealPlan = new HashMap<>();
        int dayIndex = 0;

        for (List<Recipe> recipes : rawMealPlan.values()) {
            LocalDate date = startDate.plusDays(dayIndex);
            List<GenerateMealPlanRecipeDto> dtoList = recipes.stream()
                    .map(recipe -> new GenerateMealPlanRecipeDto(recipe.getRecipeId(), recipe.getTitle()))
                    .collect(Collectors.toList());
            mealPlan.put(date, dtoList);
            dayIndex++;
        }

        return mealPlan;
    }
}
