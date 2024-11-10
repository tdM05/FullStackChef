package app;

import data_access.RecipeDataAccessObject;
import use_case.search_recipe.SearchRecipeDataAccessInterface;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;
import javax.swing.JFrame;
import java.awt.CardLayout;

/**
 * Builder for the Search Recipe Application.
 */
public class SearchRecipeApplication {

    public static void main(String[] args) {

        final SearchRecipeDataAccessInterface searchRecipeDAO = new RecipeDataAccessObject();
        final DisplayRecipeDataAccessInterface displayRecipeDAO = new RecipeDataAccessObject();

        // Set up a frame with a CardLayout to handle view switching
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Application");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Use CardLayout to manage switching between views
        CardLayout cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        final SearchRecipeAppBuilder searchBuilder = new SearchRecipeAppBuilder();
        searchBuilder.addSearchRecipeDAO(searchRecipeDAO)
               .addSearchRecipeView()
               .addSearchRecipeUseCase().build().setVisible(true);

        final DisplayRecipeAppBuilder displayBuilder = new DisplayRecipeAppBuilder();
        displayBuilder.addDisplayRecipeDAO(displayRecipeDAO)
                .addDisplayRecipeView()
                .addDisplayRecipeUseCase();

        // Add both views to the frame's CardLayout
        frame.add(searchBuilder.build().getContentPane(), "searchView");
        frame.add(displayBuilder.build().getContentPane(), "displayView");

        // Set the initial view to the search view
        cardLayout.show(frame.getContentPane(), "searchView");

        // Show the frame
        frame.setVisible(true);
    }
}
