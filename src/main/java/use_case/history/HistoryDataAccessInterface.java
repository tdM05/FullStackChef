package use_case.history;

import entity.User;

import java.util.List;

public interface HistoryDataAccessInterface {

    /**
     * Returns the list of history recipe IDs for the given user.
     * @param user the user whose history recipes are to be retrieved
     * @return the list of history recipe IDs for the given user
     */
    List<Integer> getHistory(User user);

    /**
     * Updates the system to record the user's history recipes.
     *
     * @param user the user whose history recipes are to be saved
     */
    void saveHistory(User user, List<Integer> history);
}
