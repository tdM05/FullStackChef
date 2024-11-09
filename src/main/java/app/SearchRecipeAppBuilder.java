package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_recipe.SearchRecipeController;
import interface_adapter.search_recipe.SearchRecipePresenter;
import interface_adapter.search_recipe.SearchRecipeViewModel;
import use_case.search_recipe.SearchRecipeDataAccessInterface;
import use_case.search_recipe.SearchRecipeInteractor;
import use_case.search_recipe.SearchRecipeOutputBoundary;
import view.SearchRecipeView;

/**
 * Builder for the Search Recipe Application.
 */
public class SearchRecipeAppBuilder {
    public static final int HEIGHT = 1200;
    public static final int WIDTH = 800;
    private SearchRecipeDataAccessInterface searchrecipeDAO;
    private SearchRecipeViewModel searchrecipeViewModel = new SearchRecipeViewModel();
    private SearchRecipeView searchrecipeView;
    private SearchRecipeInteractor searchrecipeInteractor;
    private ViewManagerModel viewManagerModel;

    /**
     * Sets the RecipeDAO to be used in this application.
     *
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public SearchRecipeAppBuilder addSearchRecipeDAO(SearchRecipeDataAccessInterface recipeDataAccess) {
        searchrecipeDAO = recipeDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Search Recipe Use Case and connects the SearchRecipeView to its
     * controller.
     *
     * @return this builder
     * @throws RuntimeException if this method is called before addRecipeView
     */
    public SearchRecipeAppBuilder addSearchRecipeUseCase() {
        final SearchRecipeOutputBoundary recipeOutputBoundary = new SearchRecipePresenter(viewManagerModel, searchrecipeViewModel);
        searchrecipeInteractor = new SearchRecipeInteractor(searchrecipeDAO, recipeOutputBoundary);

        final SearchRecipeController controller = new SearchRecipeController(searchrecipeInteractor);
        if (searchrecipeView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        searchrecipeView.setRecipeController(controller);
        return this;
    }

    /**
     * Creates the SearchRecipeView and underlying SearchRecipeViewModel.
     *
     * @return this builder
     */
    public SearchRecipeAppBuilder addSearchRecipeView() {
        viewManagerModel = new ViewManagerModel();
        searchrecipeViewModel = new SearchRecipeViewModel();
        searchrecipeView = new SearchRecipeView(searchrecipeViewModel);
        return this;
    }

    /**
     * Builds the application.
     *
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Search Application");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(searchrecipeView);

        return frame;
    }
}