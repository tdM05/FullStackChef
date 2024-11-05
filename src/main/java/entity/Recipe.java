package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public class Recipe {
    private int id;
    private String title;
    private String image;
    private String imageType;

    public Recipe(int id, String title, String image, String imageType) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
    }

    // Getters for each field
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getImage() {
        return image;
    }
    public String getImageType() {
        return imageType;
    }

}