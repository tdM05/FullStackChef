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
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addSearchUseCase()
                                            .addDisplayRecipeUseCase()
                                            .addCheckFavoriteUseCase()
                                            .addFavoriteUseCase()
                                            .build();
        application.setVisible(true);
        }
}
