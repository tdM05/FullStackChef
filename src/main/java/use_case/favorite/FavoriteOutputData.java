package use_case.favorite;

/**
 * The Output Data for the Favorite Recipe Use Case.
 */
public class FavoriteOutputData {
    private final boolean favorite;

    public FavoriteOutputData(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean getFavorite() {
        return favorite;
    }


}
