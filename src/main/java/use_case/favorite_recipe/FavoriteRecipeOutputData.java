package use_case.favorite_recipe;

public class FavoriteRecipeOutputData {
    private final boolean favorite;
    private final boolean useCaseFailed;

    public FavoriteRecipeOutputData(boolean favorite, boolean useCaseFailed) {
        this.favorite = favorite;
        this.useCaseFailed = useCaseFailed;
    }

    public boolean getIsFavorite() {
        return favorite;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
