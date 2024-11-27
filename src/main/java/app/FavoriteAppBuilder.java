package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.favorite.FavoriteController;
import interface_adapter.favorite.FavoritePresenter;
import interface_adapter.favorite.FavoriteViewModel;
import use_case.favorite.FavoriteDataAccessInterface;
import use_case.favorite.FavoriteInteractor;
import use_case.favorite.FavoriteOutputBoundary;
import view.FavoriteView;

/**
 * Builder for the Favorite Application.
 */
public class FavoriteAppBuilder {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 600;
    
    private FavoriteDataAccessInterface favoriteRecipeDAO;
    private FavoriteViewModel favoriteViewModel = new FavoriteViewModel();
    private FavoriteView favoriteView;
    private FavoriteInteractor favoriteRecipeInteractor;
    private ViewManagerModel viewManagerModel;
    
    
    /**
     * Sets the RecipeDAO to be used in this application.
     *
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public FavoriteAppBuilder addFavoriteDAO(FavoriteDataAccessInterface recipeDataAccess) {
        favoriteRecipeDAO = recipeDataAccess;
        return this;
    }
    
    
    /**
     * Creates the objects for the Favorite Recipe Use Case and connects the FavoriteView to its
     * controller.
     *
     * @return this builder
     * @throws RuntimeException if this method is called before addRecipeView
     */
    
    public FavoriteAppBuilder addFavoriteRecipeUseCase() {
        final FavoriteOutputBoundary recipeOutputBoundary = new FavoritePresenter(viewManagerModel, favoriteViewModel);
        favoriteRecipeInteractor = new FavoriteInteractor(favoriteRecipeDAO, recipeOutputBoundary);
        
        final FavoriteController controller = new FavoriteController(favoriteRecipeInteractor);
        if (favoriteView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        favoriteView.setFavoriteController(controller);
        return this;
    }

    public FavoriteAppBuilder addFavoriteRecipeView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = currentViewManagerModel;
        favoriteViewModel = new FavoriteViewModel();
        favoriteView = new FavoriteView(favoriteViewModel,viewManagerModel);
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

        frame.add(favoriteView);
        
        return frame;
    }

    public FavoriteView getFavoriteRecipeView() {
        return favoriteView;
    }
}
