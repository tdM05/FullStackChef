package entity;

import java.util.List;

/**
 * The representation of a favorite recipe in our program.
 */

public class Favorite {
    private final int recipeId;
    private final String title;
    private final String image;
    private final List<String> collection;

    /**
     * Constructs a Favorite with the specified details.
     *
     * @param recipeId         the unique identifier of the recipe
     * @param title            the title of the recipe
     * @param image            the image URL of the recipe
     * @param collection       the collection of the recipe
     */
    public Favorite(int recipeId, String title, String image, List<String> collection) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
        this.collection = collection;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<String> getCollection() {
        return collection;
    }

}
