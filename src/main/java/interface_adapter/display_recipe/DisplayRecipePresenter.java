package interface_adapter.display_recipe;

import use_case.display_recipe.RecipeOutputBoundary;
import use_case.display_recipe.RecipeOutputData;

/**
 * The Presenter for the Display_recipe Use Case.
 */

public class DisplayRecipePresenter implements RecipeOutputBoundary {
    /** priv */
    public DisplayRecipePresenter() {
        /** implement */
    }

    public void prepareSuccessView(RecipeOutputData response) {
        /** implement */
    }


    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that logout can't fail.
        // Thought question: is this a reasonable assumption?
    }

}
