package use_case.search_recipe;

import java.util.Objects;

/**
 * The output data for the Search Recipe Use Case.
 */
public class CommonSearchRecipeOutputData implements SearchRecipeOutputData {
    private final String recipeName;
    private final String imageUrl;
    private final int id;

    public CommonSearchRecipeOutputData(String recipeName, String imageUrl, int id) {
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    @Override
    public String recipeName() {
        return recipeName;
    }

    @Override
    public String imageUrl() {
        return imageUrl;
    }

    @Override
    public int id() {
        return id;
    }

    @SuppressWarnings({"checkstyle:ReturnCount", "checkstyle:SuppressWarnings"})
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CommonSearchRecipeOutputData other = (CommonSearchRecipeOutputData) obj;
        return recipeName.equals(other.recipeName) && imageUrl.equals(other.imageUrl) && id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeName, imageUrl, id);
    }
}
