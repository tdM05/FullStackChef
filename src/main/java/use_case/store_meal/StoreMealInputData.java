package use_case.store_meal;

import java.util.List;

/**
 * The Input Data for the Store Meal Use Case.
 */
public class StoreMealInputData {
    private int recipeId;

    public StoreMealInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
