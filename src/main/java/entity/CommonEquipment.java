package entity;

/**
 * The representation of an equipment in our program.
 */
public class CommonEquipment implements Equipment {
    private final String name;
    private final int id;
    private final String imageUrl;

    public CommonEquipment(String name, int id, String imageUrl) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String imageUrl() {
        return imageUrl;
    }
}
