package entity;

public interface User {

        /**
        * Get the name of the user.
        * @return the name of the user
        */
        String getName();

        /**
        * Get the password of the user.
        * @return the password of the user
        */
        String getPassword();

        /**
        * Set the password of the user.
        * @param password the new password
        */
        void setPassword(String password);

        /**
        * Get the favorite recipes of the user.
        * @return the favorite recipes of the user
        */
        Favorite getFavorite();

}
