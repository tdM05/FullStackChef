package entity;

/**
 * A regular user in the system.
 * Implement User interface.
 */
public class RegularUser implements User {
    private String name;
    private String password;
    private Favorite favorite;

    public RegularUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getDisplayName() {
        return name; // Default display name is the username
    }

    @Override
    public void setDisplayName(String displayName) {
        // Regular user can change their display name
        this.name = displayName;
    }

    @Override
    public Favorite getFavorite(){
        return favorite;
    }
}
