package app.builders;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.favorite_recipe.FavoriteRecipeController;
import interface_adapter.favorite_recipe.FavoriteRecipePresenter;
import interface_adapter.favorite_recipe.FavoriteRecipeViewModel;
import use_case.favorite_recipe.FavoriteRecipeDataAccessInterface;
import use_case.favorite_recipe.FavoriteRecipeInteractor;
import use_case.favorite_recipe.FavoriteRecipeOutputBoundary;
import view.FavoriteRecipeView;

/**
 * Builder for the Favorite Application.
 */
public class FavoriteAppBuilder {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 600;
    
    private FavoriteRecipeDataAccessInterface favoriteRecipeDAO;
    private FavoriteRecipeViewModel favoriteRecipeViewModel = new FavoriteRecipeViewModel();
    private FavoriteRecipeView favoriteRecipeView;
    private FavoriteRecipeInteractor favoriteRecipeInteractor;
    private ViewManagerModel viewManagerModel;
    
    
    /**
     * Sets the RecipeDAO to be used in this application.
     *
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public FavoriteAppBuilder addFavoriteDAO(FavoriteRecipeDataAccessInterface recipeDataAccess) {
        favoriteRecipeDAO = recipeDataAccess;
        return this;
    }
    
    
    /**
     * Creates the objects for the Favorite Recipe Use Case and connects the FavoriteRecipeView to its
     * controller.
     *
     * @return this builder
     * @throws RuntimeException if this method is called before addRecipeView
     */
    
    public FavoriteAppBuilder addFavoriteRecipeUseCase() {
        final FavoriteRecipeOutputBoundary recipeOutputBoundary = new FavoriteRecipePresenter(viewManagerModel, favoriteRecipeViewModel);
        favoriteRecipeInteractor = new FavoriteRecipeInteractor(favoriteRecipeDAO, recipeOutputBoundary);
        
        final FavoriteRecipeController controller = new FavoriteRecipeController(favoriteRecipeInteractor);
        if (favoriteRecipeView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        favoriteRecipeView.setFavoriteController(controller);
        return this;
    }

    public FavoriteAppBuilder addFavoriteRecipeView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = currentViewManagerModel;
        favoriteRecipeViewModel = new FavoriteRecipeViewModel();
        favoriteRecipeView = new FavoriteRecipeView(favoriteRecipeViewModel,viewManagerModel);
        return this;
    }
    
    /**
     * Builds the Favorite Application.
     */
    
    public JFrame build() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Favorite");
        frame.setSize(WIDTH, HEIGHT);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(favoriteRecipeView);
        
        return frame;
    }

    public FavoriteRecipeView getFavoriteRecipeView() {
        return favoriteRecipeView;
    }
}
