package use_case.note;

import entity.CommonUser;

/**
 * The "Use Case Interactor" for our two note-related use cases of refreshing
 * the contents of the note and saving the contents of the note. Since they
 * are closely related, we have combined them here for simplicity.
 */
public class NoteInteractor implements NoteInputBoundary {

    private final NoteDataAccessInterface noteDataAccessInterface;
    private final NoteOutputBoundary noteOutputBoundary;
    // Note: this program has it hardcoded which commonUser object it is getting data for;
    // you could change this if you wanted to generalize the code. For example,
    // you might allow a commonUser of the program to create a new note, which you
    // could store as a "commonUser" through the API OR you might maintain all notes
    // in a JSON object stored in one common "commonUser" stored through the API.
    private final CommonUser commonUser = new CommonUser("jonathan_calver2", "abc123");

    public NoteInteractor(NoteDataAccessInterface noteDataAccessInterface,
                          NoteOutputBoundary noteOutputBoundary) {
        this.noteDataAccessInterface = noteDataAccessInterface;
        this.noteOutputBoundary = noteOutputBoundary;
    }

    /**
     * Executes the refresh note use case.
     *
     */
    @Override
    public void executeRefresh() {
        try {

            final String note = noteDataAccessInterface.loadNote(commonUser);
            noteOutputBoundary.prepareSuccessView(note);
        }
        catch (DataAccessException ex) {
            noteOutputBoundary.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Executes the save note use case.
     *
     * @param note the input data
     */
    @Override
    public void executeSave(String note) {
        try {

            final String updatedNote = noteDataAccessInterface.saveNote(commonUser, note);
            noteOutputBoundary.prepareSuccessView(updatedNote);
        }
        catch (DataAccessException ex) {
            noteOutputBoundary.prepareFailView(ex.getMessage());
        }
    }
}
