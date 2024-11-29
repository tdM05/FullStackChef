package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import data_access.UpdateMealsDataAccessObject;
import data_access.UserProfile.UserProfileDao;
import interface_adapter.ViewManagerModel;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanController;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanPresenter;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanViewModel;
import interface_adapter.mealplan.update_meals.UpdateMealsController;
import interface_adapter.mealplan.update_meals.UpdateMealsPresenter;
import interface_adapter.mealplan.update_meals.UpdateMealsViewModel;
import use_case.mealplan.generate_mealplan.GenerateMealPlanDataAccessInterface;
import use_case.mealplan.generate_mealplan.GenerateMealPlanInteractor;
import use_case.mealplan.generate_mealplan.GenerateMealPlanOutputBoundary;
import use_case.mealplan.update_meals.UpdateMealsDataAccessInterface;
import use_case.mealplan.update_meals.UpdateMealsInputBoundary;
import use_case.mealplan.update_meals.UpdateMealsInteractor;
import use_case.mealplan.update_meals.UpdateMealsOutputBoundary;
import use_case.set_meals.StoreMealInputBoundary;
import use_case.set_meals.StoreMealInteractor;
import view.MealPlanView;

/**
 * Builder for the Meal Plan Application.
 */
public class MealPlanAppBuilder {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;

    private GenerateMealPlanDataAccessInterface mealPlanDAO;
    private GenerateMealPlanViewModel mealPlanViewModel = new GenerateMealPlanViewModel();
    private MealPlanView mealPlanView;
    private GenerateMealPlanInteractor mealPlanInteractor;
    private ViewManagerModel viewManagerModel;

    /**
     * Sets the MealPlanDAO to be used in this application.
     *
     * @param mealPlanDataAccess the DAO to use
     * @return this builder
     */
    public MealPlanAppBuilder addMealPlanDAO(GenerateMealPlanDataAccessInterface mealPlanDataAccess) {
        mealPlanDAO = mealPlanDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Meal Plan Use Case and connects the MealPlanView to its
     * controller.
     *
     * @return this builder
     */
    public MealPlanAppBuilder addMealPlanUseCase(UpdateMealsViewModel updateMealsViewModel) {
        final GenerateMealPlanOutputBoundary outputBoundary =
                new GenerateMealPlanPresenter(viewManagerModel, mealPlanViewModel);
        // add the set meal use case
        final UserProfileDao userProfileDao = new UserProfileDao();
        final StoreMealInputBoundary storeMealUseCase = new StoreMealInteractor(userProfileDao);
        mealPlanInteractor = new GenerateMealPlanInteractor(outputBoundary, mealPlanDAO, storeMealUseCase);

        final GenerateMealPlanController controller = new GenerateMealPlanController(mealPlanInteractor);
        if (mealPlanView == null) {
            throw new RuntimeException("addMealPlanView must be called before addMealPlanUseCase");
        }
        // add the updateMeals controller
        // TODO refactor this to be its own builder
        final UpdateMealsDataAccessInterface updateMealsDataAccess = new UpdateMealsDataAccessObject();
        final UpdateMealsOutputBoundary updateMealsPresenter = new UpdateMealsPresenter(updateMealsViewModel);
        final UpdateMealsInputBoundary updateMealsInteractor = new UpdateMealsInteractor(updateMealsPresenter,
                updateMealsDataAccess);
        final UpdateMealsController updateMealsController = new UpdateMealsController(updateMealsInteractor);
        mealPlanView.setMealPlanController(controller, updateMealsController);
        return this;
    }

    /**
     * Creates the MealPlanView and underlying MealPlanViewModel.
     * @param currentViewManagerModel the ViewManagerModel to use
     * @return this builder
     */
    public MealPlanAppBuilder addMealPlanView(ViewManagerModel currentViewManagerModel,
                                              UpdateMealsViewModel updateMealsViewModel) {
        viewManagerModel = currentViewManagerModel;
        mealPlanViewModel = new GenerateMealPlanViewModel();
        // TODO refactor this in a builder
        mealPlanView = new MealPlanView(mealPlanViewModel, updateMealsViewModel, viewManagerModel);
        return this;
    }

    /**
     * Builds the Meal Plan view into a JFrame.
     *
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Meal Plan Application");
        frame.setSize(WIDTH, HEIGHT);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(mealPlanView);

        return frame;
    }

    public MealPlanView getMealPlanView() {
        return mealPlanView;
    }
}

