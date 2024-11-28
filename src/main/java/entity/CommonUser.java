package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {
    private final String name;
    private String password;
    private Favorite favorite;
    private List<Integer> mealIds;
    private DietaryRestriction dietaryRestrictions;
    // Delete later because the notes application needs it for now but it will be deleted later so please ignore it
    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.favorite = new CommonFavorite(new ArrayList<>());
        mealIds = new ArrayList<>();
    }

    public CommonUser(String name, String password, Favorite favorite) {
        this.name = name;
        this.password = password;
        this.favorite = favorite;
        mealIds = new ArrayList<>();
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
    public List<Integer> getMealIds() {
        return mealIds;
    }

    @Override
    public void setMealIDs(List<Integer> newMealIds) {
        this.mealIds = new ArrayList<>(newMealIds);
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
