//package entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * The representation of a user profile in our program.
// */
//public class UserProfile {
//    private final User user;
//    private final List<Recipe> favoriteMeals;
//    private final List<DietaryRestriction> dietaryRestrictions;
//
//    public UserProfile(User user) {
//        this.user = user;
//        this.favoriteMeals = new ArrayList<>();
//        this.dietaryRestrictions = new ArrayList<>();
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public List<Recipe> getFavoriteMeals() {
//        return favoriteMeals;
//    }
//
//    public void addFavoriteMeals(Recipe recipe) {
//        favoriteMeals.add(recipe);
//    }
//
//    public void removeFavoriteMeals(Recipe recipe) {
//        favoriteMeals.remove(recipe);
//    }
//
//    public List<DietaryRestriction> getDietaryRestrictions() {
//        return dietaryRestrictions;
//    }
//
//    public void addDietaryRestriction(DietaryRestriction restriction) {
//        dietaryRestrictions.add(restriction);
//    }
//
//    public void removeDietaryRestriction(DietaryRestriction restriction) {
//        dietaryRestrictions.remove(restriction);
//    }
//}
