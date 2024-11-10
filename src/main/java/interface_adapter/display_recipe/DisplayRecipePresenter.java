package interface_adapter.display_recipe;

import interface_adapter.ViewManagerModel;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.display_recipe.DisplayRecipeOutputData;
import entity.Ingredient;
import entity.Instruction;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Presenter for the Display Recipe Use Case.
 * Implements the DisplayRecipeOutputBoundary to prepare data for the view.
 */
public class DisplayRecipePresenter implements DisplayRecipeOutputBoundary {

    private final ViewManagerModel viewManager;
    private final DisplayRecipeViewModel displayRecipeViewModel;

    /**
     * Constructs a DisplayRecipePresenter with the specified ViewManagerModel and DisplayRecipeViewModel.
     *
     * @param viewManager            the view manager model to handle view switching
     * @param displayRecipeViewModel the view model to update with recipe data
     */
    public DisplayRecipePresenter(ViewManagerModel viewManager, DisplayRecipeViewModel displayRecipeViewModel) {
        this.viewManager = viewManager;
        this.displayRecipeViewModel = displayRecipeViewModel;
    }

    /**
     * Prepares the success view with the provided DisplayRecipeOutputData.
     *
     * @param outputData the output data containing recipe details
     */
    @Override
    public void prepareSuccessView(DisplayRecipeOutputData outputData) {
        // Create a new DisplayRecipeState and populate it with data from outputData
        DisplayRecipeState displayRecipeState = new DisplayRecipeState();

        displayRecipeState.setTitle(outputData.getTitle());

        // Convert List<Ingredient> to List<String> for display
        List<String> ingredientStrings = outputData.getIngredients().stream()
                .map(Ingredient::toString)  // Assuming Ingredient has a meaningful toString() method
                .collect(Collectors.toList());
        displayRecipeState.setIngredients(ingredientStrings);

        // Convert List<Instruction> to List<String> for display
        List<String> instructionStrings = outputData.getInstructions().stream()
                .map(Instruction::toString)  // Assuming Instruction has a meaningful toString() method
                .collect(Collectors.toList());
        displayRecipeState.setInstructions(instructionStrings);

        displayRecipeState.setImageUrl(outputData.getImage());

        // Update the DisplayRecipeViewModel with the new state
        displayRecipeViewModel.setState(displayRecipeState);
        displayRecipeViewModel.firePropertyChanged("state");  // Notify listeners

        // Update viewManager with the new view name
        viewManager.setState(displayRecipeViewModel.getViewName());
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message explaining the failure
     */
//    @Override
//    public void prepareFailView(String errorMessage) {
//        final DisplayRecipeState displayRecipeState = displayRecipeViewModel.getState();
//        displayRecipeState.setDisplayError(errorMessage);
//        displayRecipeViewModel.firePropertyChanged();
//    }
//}
    @Override
    public void prepareFailView(String errorMessage) {
        DisplayRecipeState errorState = new DisplayRecipeState();
        errorState.setDisplayError(errorMessage);
        displayRecipeViewModel.setState(errorState);
        displayRecipeViewModel.firePropertyChanged("state");  // Notify listeners of the error state
    }
}