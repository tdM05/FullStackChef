package interface_adapter.display_recipe;

import use_case.display_recipe.RecipieOutputBoundary;
import use_case.display_recipe.RecipieOutputData;

/**
 * The Presenter for the Display_recipe Use Case.
 */

public class DisplayRecipePresenter implements RecipieOutputBoundary {
    /** priv */
    public DisplayRecipePresenter() {
        /** implement */
    }

    public void prepareSuccessView(RecipieOutputData response) {
        /** implement */
    }


    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that logout can't fail.
        // Thought question: is this a reasonable assumption?
    }

}
