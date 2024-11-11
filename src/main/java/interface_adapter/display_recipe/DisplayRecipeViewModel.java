package interface_adapter.display_recipe;

import interface_adapter.ViewModel;
import java.util.List;

/**
 * The ViewModel for displaying recipe details.
 */
public class DisplayRecipeViewModel extends ViewModel {

    private String title;
    private String imageUrl;
    private List<String> ingredients;
    private List<String> instructions;
    private String errorMessage;

    public DisplayRecipeViewModel() {
        super("display recipe");
    }

    public void updateRecipeDetails(String title, String imageUrl, List<String> ingredients, List<String> instructions) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.errorMessage = null;  // Clear any previous error message

        firePropertyChanged("state");
    }

    public void updateWithError(String errorMessage) {
        this.errorMessage = errorMessage;

        firePropertyChanged("state");
    }

    // Getters for DisplayRecipeView to retrieve updated values
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getInstructions() { return instructions; }
    public String getErrorMessage() { return errorMessage; }
}
