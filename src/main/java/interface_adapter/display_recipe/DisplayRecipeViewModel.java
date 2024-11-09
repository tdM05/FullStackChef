package interface_adapter.display_recipe;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the DisplayRecipe View.
 */
public class DisplayRecipeViewModel extends ViewModel<DisplayRecipeState> {

    /**
     * Initializes the DisplayRecipeViewModel with the default state.
     */
    public DisplayRecipeViewModel() {
        super("display recipe");
        setState(new DisplayRecipeState());
    }
}
