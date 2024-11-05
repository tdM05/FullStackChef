package interface_adapter.display_recipe;

import use_case.display_recipe.RecipieOutputBoundary;
import use_case.display_recipe.RecipieOutputData;

/**
 * The Presenter for the Display_recipe Use Case.
 */

public class RecipePresenter implements RecipieOutputBoundary {
    /** priv */
    public RecipePresenter() {
        /** implement */
    }

    public void prepareSuccessView(RecipieOutputData response) {
        /** implement */
    }


    public void prepareFailView(String error) {
        /** implement */

    }

}
