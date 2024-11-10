package interface_adapter.display_recipe;

import interface_adapter.ViewModel;
import java.util.List;

/**
 * The ViewModel for displaying recipe details.
 */
public class DisplayRecipeViewModel extends ViewModel<DisplayRecipeState> {

    public DisplayRecipeViewModel() {
        super("display recipe");
        setState(new DisplayRecipeState());
    }

    /**
     * Updates the ViewModel state with the recipe details.
     *
     * @param title The title of the recipe.
     * @param imageUrl The URL of the recipe image.
     * @param ingredients The list of ingredients for the recipe.
     * @param instructions The list of instructions for the recipe.
     */
    public void updateRecipeDetails(String title, String imageUrl, List<String> ingredients, List<String> instructions) {
        DisplayRecipeState newState = new DisplayRecipeState();
        newState.setTitle(title);
        newState.setImageUrl(imageUrl);
        newState.setIngredients(ingredients);
        newState.setInstructions(instructions);
        setState(newState);  // Set the new state
        firePropertyChanged("state");  // Notify listeners of the update
    }

    /**
     * Updates the ViewModel with an error message.
     *
     * @param errorMessage The error message to display.
     */
    public void updateWithError(String errorMessage) {
        DisplayRecipeState errorState = new DisplayRecipeState();
        errorState.setDisplayError(errorMessage);
        setState(errorState);  // Set the error state
        firePropertyChanged("state");  // Notify listeners of the update
    }
}
