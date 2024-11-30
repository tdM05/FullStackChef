package use_case.check_favorite;

/**
 * The input boundary for the Check Favorite Use Case.
 */
public interface CheckFavoriteInputBoundary {

    /**
     * Executes the Check Favorite Use Case.
     * @param checkFavoriteInputData the input data
     */
    void execute(CheckFavoriteInputData checkFavoriteInputData);
}