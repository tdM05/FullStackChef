package app;

import javax.swing.*;

/**
 * The Main class of our application.
 */
public class MainApp {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */

    public static void main(String[] args) {
        final MainAppBuilder appBuilder = new MainAppBuilder();
        final JFrame application = appBuilder
                                            .addSignupView()
                                            .addLoginView()
                                            .addSearchView()
                                            .addDisplayRecipeView()
                                            .addFavoriteView()
                                            .addGroceryListView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addSearchUseCase()
                                            .addDisplayRecipeUseCase()
                                            .addCheckFavoriteUseCase()
                                            .addGroceryListUseCase()
                                            .build();
        application.setVisible(true);
        }
}
