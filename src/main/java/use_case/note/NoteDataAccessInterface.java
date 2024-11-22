package use_case.note;

import entity.CommonUser;

/**
 * Interface for the NoteDAO. It consists of methods for
 * both loading and saving a note.
 */
public interface NoteDataAccessInterface {

    /**
     * Saves a note for a given commonUser. This will replace any existing note.
     * <p>The password of the commonUser must match that of the commonUser saved in the system.</p>
     * @param commonUser the commonUser information associated with the note
     * @param note the note to be saved
     * @return the contents of the note
     * @throws DataAccessException if the commonUser's note can not be saved for any reason
     */
    String saveNote(CommonUser commonUser, String note) throws DataAccessException;

    /**
     * Returns the note associated with the commonUser. The password
     * is not checked, so anyone can read the information.
     * @param commonUser the commonUser information associated with the note
     * @return the contents of the note
     * @throws DataAccessException if the commonUser's note can not be loaded for any reason
     */
    String loadNote(CommonUser commonUser) throws DataAccessException;

}
