package app;

import data_access.Constants;
import data_access.FavoriteDataAccessObject;
import data_access.RecipeDataAccessObject;
import data_access.grocery_list.GroceryListDataAccessObject;

import interface_adapter.ViewManagerModel;
import use_case.favorite_recipe.FavoriteRecipeDataAccessInterface;
import use_case.grocery_list.GroceryListDataAccessInterface;
import use_case.search_recipe.SearchRecipeDataAccessInterface;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;
import view.ViewManager;

import javax.swing.JFrame;
import java.awt.CardLayout;

public class SearchRecipeApplication {

    public static void main(String[] args) {

        final SearchRecipeDataAccessInterface searchRecipeDAO = new RecipeDataAccessObject();
        final DisplayRecipeDataAccessInterface displayRecipeDAO = new RecipeDataAccessObject();
        final GroceryListDataAccessInterface groceryListDAO = new GroceryListDataAccessObject();
        final FavoriteRecipeDataAccessInterface favoriteDAO = new FavoriteDataAccessObject();

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


        // Set up view manager
        final ViewManagerModel viewManagerModel= new ViewManagerModel();
        // DO NOT DELETE THIS LINE
        @SuppressWarnings("unused")
        final ViewManager viewManager = new ViewManager(frame.getContentPane(), cardLayout, viewManagerModel);


        final SearchRecipeAppBuilder searchBuilder = new SearchRecipeAppBuilder();
        searchBuilder.addSearchRecipeDAO(searchRecipeDAO)
                .addSearchRecipeView(viewManagerModel)
                .addSearchRecipeUseCase();

        final DisplayRecipeAppBuilder displayBuilder = new DisplayRecipeAppBuilder();
        displayBuilder.addDisplayRecipeDAO(displayRecipeDAO)
                .addDisplayRecipeView(viewManagerModel)
                .addDisplayRecipeUseCase();

        // We need to add things in this order
        final GroceryListAppBuilder groceryListBuilder = new GroceryListAppBuilder();
        groceryListBuilder.addGroceryListDAO(new GroceryListDataAccessObject())
                .addGroceryListView(viewManagerModel)
                .addGroceryListUseCase();

        final FavoriteAppBuilder favoriteBuilder = new FavoriteAppBuilder();
        favoriteBuilder.addFavoriteDAO(favoriteDAO)
                .addFavoriteRecipeView(viewManagerModel)
                .addFavoriteRecipeUseCase();

        // Add both views to the frame's CardLayout
        frame.add(searchBuilder.build().getContentPane(), Constants.SEARCH_VIEW);
        frame.add(displayBuilder.build().getContentPane(), Constants.DISPLAY_RECIPE_VIEW);
        frame.add(groceryListBuilder.build().getContentPane(), Constants.GROCERY_LIST_VIEW);
        frame.add(favoriteBuilder.build().getContentPane(), Constants.FAVORITE_VIEW);

        // Set the initial view to the search view
        cardLayout.show(frame.getContentPane(), Constants.SEARCH_VIEW);

        frame.setVisible(true);
    }
}
