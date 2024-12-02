package app;

import entity.GuestUser;
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

    public void clearUser() {
        this.user = null;
    }

    public boolean isGuestUser() {
        return user instanceof GuestUser;
    }

    public boolean isRegularUser() {
        return !isGuestUser();
    }
}
