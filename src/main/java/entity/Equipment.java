package entity;

/**
 * The representation of an equipment which will be used to tell what the equipment is needed for a recipe.
 */
public interface Equipment {
    /**
     * Gets the name of the equipment.
     * @return the name of the equipment.
     */
    String getName();

    /**
     * Gets the ID of the equipment.
     * @return the ID of the equipment.
     */
    int getID();

    /**
     * Gets the image URL of the equipment.
     * @return the image URL of the equipment.
     */
    String imageUrl();
}
