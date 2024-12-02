package use_case.history;

/**
 * This Input Data for the Add History Recipe Use Case
 */
public class HistoryInputData {
    private final int recipeId;

    public HistoryInputData(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
