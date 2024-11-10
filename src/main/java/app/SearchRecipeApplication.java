package app;

import data_access.RecipeDataAccessObject;
import use_case.search_recipe.SearchRecipeDataAccessInterface;

/**
 * Builder for the Search Recipe Application.
 */
public class SearchRecipeApplication {

    public static void main(String[] args) {

        final SearchRecipeDataAccessInterface searchRecipeDAO = new RecipeDataAccessObject();

        final SearchRecipeAppBuilder builder = new SearchRecipeAppBuilder();
        builder.addSearchRecipeDAO(searchRecipeDAO)
               .addSearchRecipeView()
               .addSearchRecipeUseCase().build().setVisible(true);
    }
}
