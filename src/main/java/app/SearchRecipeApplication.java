package app;

import data_access.RecipeDataAccessObject;
import data_access.grocery_list.GroceryListDataAccessObject;
import interface_adapter.ViewManagerModel;
import use_case.grocery_list.GroceryListDataAccessInterface;
import use_case.search_recipe.SearchRecipeDataAccessInterface;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;
import view.Profile;
import view.ViewManager;

import javax.swing.JFrame;
import java.awt.CardLayout;

public class SearchRecipeApplication {

    public static void main(String[] args) {

        final SearchRecipeDataAccessInterface searchRecipeDAO = new RecipeDataAccessObject();
        final DisplayRecipeDataAccessInterface displayRecipeDAO = new RecipeDataAccessObject();
        final GroceryListDataAccessInterface groceryListDAO = new GroceryListDataAccessObject();

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
        ViewManagerModel viewManagerModel= new ViewManagerModel();
        final ViewManager viewManager = new ViewManager(frame.getContentPane(), cardLayout, viewManagerModel);


        final SearchRecipeAppBuilder searchBuilder = new SearchRecipeAppBuilder();
        searchBuilder.addSearchRecipeDAO(searchRecipeDAO)
                .addSearchRecipeView(viewManagerModel)
                .addSearchRecipeUseCase();

        final DisplayRecipeAppBuilder displayBuilder = new DisplayRecipeAppBuilder();
        displayBuilder.addDisplayRecipeDAO(displayRecipeDAO)
                .addDisplayRecipeView()
                .addDisplayRecipeUseCase();

        // We need to add things in this order
        final GroceryListAppBuilder groceryListBuilder = new GroceryListAppBuilder();
        groceryListBuilder.addGroceryListDAO(new GroceryListDataAccessObject())
                .addGroceryListView(viewManagerModel)
                .addGroceryListUseCase();

        // Add both views to the frame's CardLayout
        frame.add(searchBuilder.build().getContentPane(), "searchView");
        frame.add(displayBuilder.build().getContentPane(), "displayView");
        frame.add(groceryListBuilder.build().getContentPane(), "groceryListView");


        // Set the initial view to the search view
        cardLayout.show(frame.getContentPane(), "searchView");

        // Event listener to switch to Display view when a recipe is clicked
        searchBuilder.getSearchRecipeView().addRecipeClickListener(recipeId -> {
            displayBuilder.getDisplayRecipeView().loadRecipeDetails(recipeId);
            cardLayout.show(frame.getContentPane(), "displayView");
        });


        // Event listener to switch to GroceryList view when GroceryList button is clicked
//        final Profile profile = searchBuilder.getSearchRecipeView().getProfile();
//        profile.getGroceryListButton().addActionListener(evt -> {
//            // this should update the viewmanagermodel.
//
//
//            cardLayout.show(frame.getContentPane(), "groceryListView");
//            groceryListBuilder.getGroceryListView().updateGroceryList();
//        });


        // Optionally add a back button in DisplayRecipeView to go back to SearchRecipeView
        displayBuilder.getDisplayRecipeView().addBackButtonListener(() -> cardLayout.show(frame.getContentPane(), "searchView"));
        groceryListBuilder.getGroceryListView().addBackButtonListener(() -> cardLayout.show(frame.getContentPane(), "searchView"));
        // Show the frame
        frame.setVisible(true);
    }
}
