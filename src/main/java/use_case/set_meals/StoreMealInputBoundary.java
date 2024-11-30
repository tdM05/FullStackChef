package use_case.set_meals;

import entity.User;

/**
 * The input boundary for the Store Meal Use Case.
 */
public interface StoreMealInputBoundary {
    /**
     * Executes the Store Meal Use Case.
     * This will update the session user as well as the remove user in the api
     * @param inputData contains the recipe id to store
     * @param user is the current session user who wants to store the meal
     */
    void execute(StoreMealInputData inputData, User user);
}
