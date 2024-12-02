package entity;

import java.util.List;

/**
 * The representation of a guest user in our program.
 * A GuestUser has limited functionality: it cannot update its password or display name.
 */
public class GuestUser implements User {
    private final String username = "Guest";
    private final String password = "guest";

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        // Guests cannot change their password
        throw new UnsupportedOperationException("Guest users cannot update passwords.");
    }

    @Override
    public String getDisplayName() {
        return "Guest"; // Fixed display name
    }

    @Override
    public void setDisplayName(String displayName) {
        // Guests cannot change their display name
        throw new UnsupportedOperationException("Guest users cannot update display names.");
    }

    @Override
    public Favorite getFavorite() {
        // Returns an empty favorite list since GuestUser data is not persisted
        return new Favorite() {
            /**
             * Get the list of favorite recipe IDs.
             *
             * @return the list of favorite recipe IDs
             */
            @Override
            public List<Integer> getFavoriteRecipes() {
                return List.of();
            }
        };
    }
}
