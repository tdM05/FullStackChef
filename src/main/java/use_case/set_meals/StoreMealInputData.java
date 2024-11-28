package use_case.set_meals;

import java.util.List;
import java.util.Map;

/**
 * The Input Data for the Store Meal Use Case.
 */
public class StoreMealInputData {
    private Map<String, List<Integer>> mealIds;

    public StoreMealInputData(Map<String, List<Integer>> mealIds) {
        this.mealIds = mealIds;
    }

    public Map<String, List<Integer>> getMealIds() {
        return mealIds;
    }
}
