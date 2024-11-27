package use_case.store_meal;

import app.SessionManager;
import entity.User;

import java.util.List;

public class StoreMealInteractor implements StoreMealInputBoundary{
    private final StoreMealDataAccessInterface dataAccess;

    public StoreMealInteractor(StoreMealDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(StoreMealInputData inputData) {
        final List<Integer> mealIds = inputData.getRecipeIds();
        final SessionManager sessionManager = SessionManager.getInstance();
        final User user = sessionManager.getCurrentUser();
        // Add this meal to the actual api
        dataAccess.setMeals(mealIds);

        // Add this meal locally to the user
        user.setMealIDs(mealIds);
    }
}
