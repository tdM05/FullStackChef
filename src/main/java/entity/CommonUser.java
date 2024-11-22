package entity;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {

    private final String name;
    private String password;
    private final Favorite favorite;

    public CommonUser(String name, String password, Favorite favorite) {
        this.name = name;
        this.password = password;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Favorite getFavorite() {
        return favorite;
    }
}
