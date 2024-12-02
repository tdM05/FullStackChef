package entity;

import java.util.List;

/**
 * The representation of a history in our program.
 */
public interface History {

    /**
     * Get the list of history recipe IDs.
     * @return the list of history recipe IDs
     */
    List<Integer> getHistoryRecipes();
}
