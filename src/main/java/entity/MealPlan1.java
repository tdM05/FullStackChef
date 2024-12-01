package entity;

import java.time.LocalDate;
import java.util.List;

public interface MealPlan1 {
    public List<Recipe> getRecipes();

    public LocalDate getDate();
}
