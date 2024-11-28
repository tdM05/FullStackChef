package entity;

import java.time.LocalDate;
import java.util.List;

public class CommonMealPlan implements MealPlan {
    private final List<Recipe> recipes;
    private final LocalDate dates;

    public CommonMealPlan(List<Recipe> recipes, LocalDate dates) {
        this.dates = dates;
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public LocalDate getDate() {
        return dates;
    }
}
