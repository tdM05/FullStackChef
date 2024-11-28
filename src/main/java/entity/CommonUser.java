package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {
    private final String name;
    private String password;
    private Favorite favorite;
    private Map<String, List<Integer>> mealIds;
    private DietaryRestriction dietaryRestrictions;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.favorite = new CommonFavorite(new ArrayList<>());
        mealIds = new HashMap<>();
    }

    public CommonUser(String name, String password, Favorite favorite) {
        this.name = name;
        this.password = password;
        this.favorite = favorite;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Favorite getFavorite() {
        return favorite;
    }

    @Override
    public void setFavorite(Favorite newFavorite) {
        this.favorite = newFavorite;
    }

    @Override
    public Map<String, List<Integer>> getMealIds() {
        return mealIds;
    }

    @Override
    public void setMealIDs(Map<String, List<Integer>> newMealIds) {
        this.mealIds = newMealIds;
    }

    @Override
    public DietaryRestriction getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    @Override
    public void setDietaryRestrictions(DietaryRestriction newDietaryRestrictions) {
        this.dietaryRestrictions = newDietaryRestrictions;
    }
}
