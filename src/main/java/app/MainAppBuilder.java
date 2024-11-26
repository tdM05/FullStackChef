//package app;
//
//import interface_adapter.display_recipe.DisplayRecipeController;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class MainAppBuilder {
//    private JFrame frame;
//    private CardLayout cardLayout;
//
//    private SearchRecipeAppBuilder searchRecipeAppBuilder;
//    private DisplayRecipeAppBuilder displayRecipeAppBuilder;
//
//    public MainAppBuilder() {
//        frame = new JFrame("Recipe App");
//        cardLayout = new CardLayout();
//        searchRecipeAppBuilder = new SearchRecipeAppBuilder();
//        displayRecipeAppBuilder = new DisplayRecipeAppBuilder();
//    }
//
//    public MainAppBuilder addBuilder(JFrame frame, String constraint) {
//        final Container contentPane = frame.getContentPane();
//        frame.add(contentPane, constraint);
//        return this;
//    }
//
//    public MainAppBuilder configureEventListeners() {
//        // Event listener to switch to Display view when a recipe is clicked
//        searchBuilder.getSearchRecipeView().addRecipeClickListener(recipeId -> {
//            displayBuilder.getDisplayRecipeView().loadRecipeDetails(recipeId);
//            cardLayout.show(frame.getContentPane(), "displayView");
//        });
//    }
//
//
//}
