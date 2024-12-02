package entity;

import java.util.List;

public class CommonHistory implements History {
    private final List<Integer> historyRecipe;

    public CommonHistory(List<Integer> historyRecipe) {
        this.historyRecipe = historyRecipe;
    }

    @Override
    public List<Integer> getHistoryRecipes() {
        return historyRecipe;
    }
}
