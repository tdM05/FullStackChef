package entity;

import java.util.List;

/**
 * The representation of a favorite recipe in our program.
 */
public interface Favorite {

    /**
     * Get the list of favorite recipe IDs.
     * @return the list of favorite recipe IDs
     */
    List<Integer> getFavoriteRecipes();

}
