package use_case.check_favorite;

/**
 * The Output Data for the Check Favorite Use Case.
 */
public class CheckFavoriteOutputData {
    private final boolean favorite;

    public CheckFavoriteOutputData(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean getFavorite() {
        return favorite;
    }
}
