package app;

import entity.User;

/**
 * The Session Manager for the application.
 * This uses singleton design pattern.
 */
public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {

    }

    /**
     * Get the instance of the Session Manager.
     * @return the instance of the Session Manager.
     */
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
