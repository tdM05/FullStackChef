package use_case.display_history;


import app.SessionUser;
import entity.Recipe;
import entity.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayHistoryInteractor implements DisplayHistoryInputBoundary {

    private final DisplayHistoryDataAccessInterface dataAccess;
    private final DisplayHistoryOutputBoundary presenter;

    public DisplayHistoryInteractor(DisplayHistoryDataAccessInterface dataAccess,
                                    DisplayHistoryOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        User user = SessionUser.getInstance().getUser();

        if (user == null) {
            presenter.prepareFailView("Error with retrieving user");
            return;
        }

        List<Integer> history = dataAccess.getHistory(user);

        StringBuilder recipeIds = new StringBuilder();
        for (int historyItem : history) {
            recipeIds.append(historyItem).append(",");
        }

        if (recipeIds.length() > 0) {
            recipeIds.setLength(recipeIds.length() - 1);
        }

        String recipeList = recipeIds.toString();

        try {
            List<Recipe> recipes = dataAccess.getRecipes(recipeList);
            final List<DisplayHistoryOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
            presenter.prepareSuccessView(outputData);
        } catch (IOException | JSONException e) {
            presenter.prepareFailView("Error with retrieving recipes");
        }
    }

    @Override
    public void switchToSearchView() {
        presenter.switchToSearchView();
    }

    private List<DisplayHistoryOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<DisplayHistoryOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new DisplayHistoryOutputData(recipe.getRecipeId(),recipe.getTitle(),recipe.getImage()));
        }
        return outputData;
    }

}
