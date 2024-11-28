package use_case.store_meal;

import entity.User;

/**
 * The input boundary for the Store Meal Use Case.
 */
public interface StoreMealInputBoundary {
    /**
     * Executes the Store Meal Use Case.
     * At a high level, this turns the user into a JSONObject which is the body of the
     * api call but before sending this body to the dao, it adds the mealId to the body.
     * @param inputData contains the recipe id to store
     * @param user is the current session user who wants to store the meal
     */
    void execute(StoreMealInputData inputData, User user);
}
