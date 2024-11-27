package use_case.store_meal;

import java.util.List;

/**
 * The data access interface for storing meals.
 */
public interface StoreMealDataAccessInterface {
    /**
     * Sets the meals in the database.
     * @param mealIds The list of meal ids to store.
     */
    void setMeals(List<Integer> mealIds);
}
