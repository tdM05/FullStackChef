package data_access.user_profile;

import entity.*;
import entity.user_profile.GuestUser;
import entity.user_profile.RegularUser;
import entity.user_profile.User;
import use_case.user_profile.change_password.ChangePasswordUserDataAccessInterface;
import use_case.user_profile.login.LoginUserDataAccessInterface;
import use_case.user_profile.logout.LogoutUserDataAccessInterface;
import use_case.user_profile.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the DAO for GuestUser.
 * This implementation does NOT persist data between runs of the program.
 */
public class InMemoryGuestUserDataAccessObject implements
        SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final String guestUsername = "Guest";
    private String currentUsername;

    public InMemoryGuestUserDataAccessObject() {
        // Initialize with a default GuestUser
        users.put(guestUsername, new GuestUser());
        currentUsername = guestUsername;
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        if (user instanceof GuestUser) {
            throw new UnsupportedOperationException("GuestUser data cannot be saved.");
        }
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        if (guestUsername.equals(username)) {
            return users.get(guestUsername); // Always return the single GuestUser
        }
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        if (user instanceof GuestUser) {
            throw new UnsupportedOperationException("GuestUser cannot change password.");
        }
        users.put(user.getName(), user); // Update password for regular users
    }

    @Override
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User does not exist: " + username);
        }
        currentUsername = username;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Handles dietary restrictions for GuestUser and other users.
     * @param dietaryRestriction the dietary restriction to be set
     */
    public void setDietaryRestriction(DietaryRestriction dietaryRestriction) {
        if (guestUsername.equals(currentUsername)) {
            // Guests can have dietary restrictions but they are not persisted
            System.out.println("Guest dietary restrictions updated: " + dietaryRestriction.getDiets());
        } else {
            // Persist dietary restrictions for regular users
            User user = users.get(currentUsername);
            if (user instanceof RegularUser) {
                // Logic to save dietary restrictions for regular users
                System.out.println("Regular user dietary restrictions updated: " + dietaryRestriction.getDiets());
            }
        }
    }

    /**
     * Handles meal plans for GuestUser and other users.
     * @param mealPlan the meal plan to be set
     */
    public void setMealPlan(MealPlan mealPlan) {
        if (guestUsername.equals(currentUsername)) {
            // Guests can have meal plans but they are not persisted
            System.out.println("Guest meal plan set for date: " + mealPlan.getDate());
        } else {
            // Persist meal plans for regular users
            User user = users.get(currentUsername);
            if (user instanceof RegularUser) {
                // Logic to save meal plans for regular users
                System.out.println("Regular user meal plan set for date: " + mealPlan.getDate());
            }
        }
    }

    /**
     * Resets the application to guest mode.
     */
    public void switchToGuestMode() {
        currentUsername = guestUsername;
        System.out.println("Switched to Guest Mode.");
    }
}