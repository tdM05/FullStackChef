package interface_adapter.display_recipe;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import use_case.display_recipe.DisplayRecipeOutputData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Presenter for the Display Recipe Use Case.
 */
public class DisplayRecipePresenter implements DisplayRecipeOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final SearchViewModel searchViewModel;
    private final DisplayRecipeViewModel displayRecipeViewModel;

    public DisplayRecipePresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel, DisplayRecipeViewModel displayRecipeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
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

        final DisplayRecipeState displayRecipeState = displayRecipeViewModel.getState();
        this.displayRecipeViewModel.setState(displayRecipeState);
        this.displayRecipeViewModel.firePropertyChanged();

        this.viewManagerModel.setState(displayRecipeViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        displayRecipeViewModel.updateWithError(errorMessage);
    }

    @Override
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
