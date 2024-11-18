package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a password-protected user for our program.
 */
public class User {

    private final String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
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
}
