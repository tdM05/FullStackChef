package use_case.favorite;

/**
 * The input boundary for the Favorite Recipe Use Case.
 */
public interface FavoriteInputBoundary {

    /**
     * Executes the Favorite Recipe Use Case. Dynamically determines whether to add
     * or delete a recipe based on its current state in the user's favorites.
     * @param favoriteInputData the input data for the use case
     */
    void execute(FavoriteInputData favoriteInputData);

}
