package use_case.search_recipe;

/**
 * The output data interface for the Search Recipe Use Case.
 */
public interface SearchRecipeOutputData {

    /**
     * Gets the name of the recipe.
     *
     * @return the recipe name
     */
    String recipeName();

    /**
     * Gets the URL of the recipe image.
     *
     * @return the image URL
     */
    String imageUrl();

    /**
     * Gets the ID of the recipe.
     *
     * @return the recipe ID
     */
    int id();
}
