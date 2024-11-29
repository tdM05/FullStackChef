package use_case.display_favorite;

import app.SessionUser;
import entity.CommonUser;
import entity.Recipe;
import entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The interactor for the Display Favorite Use Case.
 */
public class DisplayFavoriteInteractor implements DisplayFavoriteInputBoundary {

    private final DisplayFavoriteDataAccessInterface dataAccess;
    private final DisplayFavoriteOutputBoundary presenter;

    // The user is stored in the session.
    //private final User user = SessionUser.getInstance().getUser();
    private final User user = new CommonUser("jonathan_calver2", "abc123");

    public DisplayFavoriteInteractor(DisplayFavoriteDataAccessInterface dataAccess,
                                     DisplayFavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        List<Integer> favorites = dataAccess.getFavorites(user);

        StringBuilder recipeIds = new StringBuilder();
        for (int favorite : favorites) {
            recipeIds.append(favorite).append(",");
        }

        String recipeList = recipeIds.toString();

        List<Recipe> recipes = dataAccess.getRecipes(recipeList);

        final List<DisplayFavoriteOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
        presenter.prepareSuccessView(outputData);

    }

    private List<DisplayFavoriteOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<DisplayFavoriteOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new DisplayFavoriteOutputData(recipe.getRecipeId(), recipe.getTitle(), recipe.getImage()));
        }
        return outputData;
    }
}
