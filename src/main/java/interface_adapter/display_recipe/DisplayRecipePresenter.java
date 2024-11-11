package interface_adapter.display_recipe;

import interface_adapter.ViewManagerModel;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.display_recipe.DisplayRecipeOutputData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Presenter for the Display Recipe Use Case.
 */
public class DisplayRecipePresenter implements DisplayRecipeOutputBoundary {

    private final ViewManagerModel viewManager;
    private final DisplayRecipeViewModel displayRecipeViewModel;

    public DisplayRecipePresenter(ViewManagerModel viewManager, DisplayRecipeViewModel displayRecipeViewModel) {
        this.viewManager = viewManager;
        this.displayRecipeViewModel = displayRecipeViewModel;
    }

    @Override
    public void prepareSuccessView(DisplayRecipeOutputData outputData) {
        System.out.println("DisplayRecipePresenter updating ViewModel with recipe details");

        // Convert ingredients and instructions to display-friendly strings
        List<String> ingredientStrings = outputData.getIngredients().stream()
                .map(ingredient -> ingredient.getName() + " - " + ingredient.getAmount() + " " + ingredient.getUnit())
                .collect(Collectors.toList());

        List<String> instructionStrings = outputData.getInstructions().stream()
                .map(instruction -> "Step " + instruction.getNumber() + ": " + instruction.getDescription())
                .collect(Collectors.toList());

        // Update ViewModel
        displayRecipeViewModel.updateRecipeDetails(
                outputData.getTitle(),
                outputData.getImage(),
                ingredientStrings,
                instructionStrings
        );

        // Switch the view state
        viewManager.setState(displayRecipeViewModel.getViewName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        displayRecipeViewModel.updateWithError(errorMessage);
    }
}
