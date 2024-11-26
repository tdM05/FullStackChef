package use_case.display_favorite;


import entity.Recipe;

import java.util.List;

/**
 * Interface for the DisplayFavorite use case
 */
public interface DisplayFavoriteDataAccessInterface {

    /**
     * Get the list of favorite recipes
     * @param recipeIdList the list of recipe ids
     * @return the list of favorite recipes
     */
    List<Recipe> getFavoriteRecipes(List<String> recipeIdList);

}
