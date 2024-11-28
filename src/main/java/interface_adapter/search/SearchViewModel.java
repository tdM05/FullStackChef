package interface_adapter.search;

import java.util.List;

import interface_adapter.ViewModel;
import use_case.search.SearchOutputData;

/**
 * ViewModel for the Search Recipe Use Case.
 * Encapsulate the state management within the SearchRecipeViewModel
 * such that the view interacts with the ViewModel rather than directly with the State
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super("searchView");
        setState(new SearchState());
    }

    public List<SearchOutputData> getRecipes() {
        return getState().getRecipe();
    }

    public void setRecipes(List<SearchOutputData> recipes) {
        getState().setRecipe(recipes);
        firePropertyChanged("recipes");
    }

    public String getError() {
        return getState().getError();
    }

    public void setError(String error) {
        getState().setError(error);
        firePropertyChanged("error");
    }
}
