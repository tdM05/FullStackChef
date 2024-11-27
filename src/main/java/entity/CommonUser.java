package entity;

import java.util.ArrayList;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {
    private final String name;
    private String password;

    // Delete later because the notes application needs it for now but it will be deleted later so please ignore it
    public CommonUser(String name, String password) {
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

}
