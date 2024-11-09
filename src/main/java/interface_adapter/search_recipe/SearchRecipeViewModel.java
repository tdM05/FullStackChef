package interface_adapter.search_recipe;

import interface_adapter.ViewModel;
import entity.Recipe;
import java.util.List;

/**
 * ViewModel for the Search Recipe Use Case.
 * Encapsulate the state management within the SearchRecipeViewModel
 * such that the view interacts with the ViewModel rather than directly with the State
 */
public class SearchRecipeViewModel extends ViewModel<SearchRecipeState> {

    public SearchRecipeViewModel() {
        super("search recipe");
        setState(new SearchRecipeState());
    }

    public List<Recipe> getRecipes() {
        return getState().getRecipe();
    }

    public void setRecipes(List<Recipe> recipes) {
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