package interface_adapter.search_recipe;

import interface_adapter.ViewModel;
import entity.Recipe;
import java.util.List;

/**
 * ViewModel for the Search Recipe Use Case.
 */
public class SearchRecipeViewModel extends ViewModel<List<Recipe>> {

    private String error;

    public SearchRecipeViewModel() {
        super("search recipe");
    }

    public List<Recipe> getRecipes() {
        return getState();
    }

    public void setRecipes(List<Recipe> recipes) {
        setState(recipes);
        firePropertyChanged("recipes");
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        firePropertyChanged("error");
    }
}
