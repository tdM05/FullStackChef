package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonMealPlan implements MealPlan {
    private final Map<LocalDate, List<Recipe>> mealPlan;
    private final LocalDate date;

    public CommonMealPlan(LocalDate date) {
        this.mealPlan = new HashMap<>();
        this.date = date;
    }

    @Override
    public void addRecipeToDate(LocalDate date, Recipe recipe) {
        mealPlan.putIfAbsent(date, new ArrayList<>());
        mealPlan.get(date).add(recipe);
    }

    @Override
    public void removeRecipeFromDate(LocalDate date, Recipe recipe) {
        if (mealPlan.containsKey(date)) {
            mealPlan.get(date).remove(recipe);
        }
    }

    @Override
    public List<Recipe> getRecipesForDate(LocalDate date) {
        return mealPlan.getOrDefault(date, new ArrayList<>());
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> allRecipes = new ArrayList<>();
        for (List<Recipe> recipes : mealPlan.values()) {
            allRecipes.addAll(recipes);
        }
        return allRecipes;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }
}

