package app;

import entity.User;

/**
 * The Session User.
 */
public class SessionUser {
    private static SessionUser instance;
    private User user;

    private SessionUser() {

    }

    public static SessionUser getInstance() {
        if (instance == null) {
            instance = new SessionUser();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
