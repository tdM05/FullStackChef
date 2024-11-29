package app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import data_access.dietaryrestrictions.DietaryRestrictionDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanController;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanPresenter;
import interface_adapter.mealplan.generate_mealplan.GenerateMealPlanViewModel;
import use_case.mealplan.generate_mealplan.GenerateMealPlanDataAccessInterface;
import use_case.mealplan.generate_mealplan.GenerateMealPlanInteractor;
import use_case.mealplan.generate_mealplan.GenerateMealPlanOutputBoundary;
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
    private DietaryRestrictionDataAccessObject dietaryAccess;

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
     * Sets the DietaryRestrictionDataAccessObject for handling dietary restrictions.
     *
     * @param dietaryDataAccess the DAO for dietary restrictions
     * @return this builder
     */
    public MealPlanAppBuilder addDietaryDataAccess(DietaryRestrictionDataAccessObject dietaryDataAccess) {
        this.dietaryAccess = dietaryDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Meal Plan Use Case and connects the MealPlanView to its
     * controller.
     *
     * @return this builder
     */
    public MealPlanAppBuilder addMealPlanUseCase() {
        final GenerateMealPlanOutputBoundary outputBoundary =
                new GenerateMealPlanPresenter(viewManagerModel, mealPlanViewModel);

        // Pass dietaryAccess only if it's available, otherwise pass null or an empty dietary access object.
        if (dietaryAccess == null) {
            System.out.println("Dietary restrictions are not set. Defaulting to no restrictions.");
        }

        mealPlanInteractor = new GenerateMealPlanInteractor(
                outputBoundary,
                mealPlanDAO,
                dietaryAccess // This can be null, and the interactor must handle it
        );

        final GenerateMealPlanController controller = new GenerateMealPlanController(mealPlanInteractor);

        if (mealPlanView == null) {
            throw new RuntimeException("addMealPlanView must be called before addMealPlanUseCase");
        }
        mealPlanView.setMealPlanController(controller);
        return this;
    }

    /**
     * Creates the MealPlanView and underlying MealPlanViewModel.
     * @param currentViewManagerModel the ViewManagerModel to use
     * @return this builder
     */
    public MealPlanAppBuilder addMealPlanView(ViewManagerModel currentViewManagerModel) {
        viewManagerModel = currentViewManagerModel;
        mealPlanViewModel = new GenerateMealPlanViewModel();
        mealPlanView = new MealPlanView(mealPlanViewModel);
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

