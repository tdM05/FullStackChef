package use_case.store_meal;

import org.json.JSONObject;

/**
 * The data access interface for storing meals.
 */
public interface StoreMealDataAccessInterface {
    /**
     * Set the meals of the user.
     * Precondition: The user has logged in.
     * @param userJSON This is what you get when you call UserToJSON.userToJSON(user)
     */
    void setInfo(JSONObject userJSON);
}
