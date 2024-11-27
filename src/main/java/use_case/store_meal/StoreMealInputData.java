package use_case.store_meal;

import java.util.List;

/**
 * The Input Data for the Store Meal Use Case.
 */
public class StoreMealInputData {
    private List<Integer> recipeIds;

    public StoreMealInputData(List<Integer> recipeIds) {
        this.recipeIds = recipeIds;
    }

    public List<Integer> getRecipeIds() {
        return recipeIds;
    }

    public void setRecipeIds(List<Integer> recipeIds) {
        this.recipeIds = recipeIds;
    }
}
