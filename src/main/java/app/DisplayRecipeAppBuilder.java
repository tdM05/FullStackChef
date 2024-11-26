package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipePresenter;
import interface_adapter.display_recipe.DisplayRecipeViewModel;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;
import use_case.display_recipe.DisplayRecipeInteractor;
import use_case.display_recipe.DisplayRecipeOutputBoundary;
import view.DisplayRecipeView;
import view.ViewManager;

/**
 * Builder for the Display Recipe Application.
 */
public class DisplayRecipeAppBuilder {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;

    private DisplayRecipeDataAccessInterface displayRecipeDAO;
    private DisplayRecipeViewModel displayRecipeViewModel = new DisplayRecipeViewModel();
    private DisplayRecipeView displayRecipeView;
    private DisplayRecipeInteractor displayRecipeInteractor;
    private ViewManagerModel viewManagerModel;

    /**
     * Sets the RecipeDAO to be used in this application.
     *
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public DisplayRecipeAppBuilder addDisplayRecipeDAO(DisplayRecipeDataAccessInterface recipeDataAccess) {
        displayRecipeDAO = recipeDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Display Recipe Use Case and connects the DisplayRecipeView to its
     * controller.
     *
     * @return this builder
     */
    public DisplayRecipeAppBuilder addDisplayRecipeUseCase() {
        final DisplayRecipeOutputBoundary recipeOutputBoundary = new DisplayRecipePresenter(viewManagerModel, displayRecipeViewModel);
        displayRecipeInteractor = new DisplayRecipeInteractor(recipeOutputBoundary, displayRecipeDAO);

        final DisplayRecipeController controller = new DisplayRecipeController(displayRecipeInteractor);
        if (displayRecipeView == null) {
            throw new RuntimeException("addDisplayRecipeView must be called before addDisplayRecipeUseCase");
        }
        displayRecipeView.setRecipeController(controller);
        return this;
    }

    /**
     * Creates the DisplayRecipeView and underlying DisplayRecipeViewModel.
     * @param currentViewManagerModel the ViewManagerModel to use
     * @return this builder
     */
    public DisplayRecipeAppBuilder addDisplayRecipeView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = currentViewManagerModel;
        displayRecipeViewModel = new DisplayRecipeViewModel();
        displayRecipeView = new DisplayRecipeView(displayRecipeViewModel, viewManagerModel);
        return this;
    }

    /**
     * Builds the Display Recipe view into a JFrame.
     *
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Display Application");
        frame.setSize(WIDTH, HEIGHT);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(displayRecipeView);

        return frame;
    }

    public DisplayRecipeView getDisplayRecipeView() {
        return displayRecipeView;
    }
}
