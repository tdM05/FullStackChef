package entity;

import java.time.LocalDate;
import java.util.List;

public class CommonWeeklyMeal implements WeeklyMeal {
    private final List<Recipe> recipes;
    private final LocalDate dates;

    public CommonWeeklyMeal(List<Recipe> recipes, LocalDate dates) {
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
