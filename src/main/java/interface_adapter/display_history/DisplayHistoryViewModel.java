package interface_adapter.display_history;

import interface_adapter.ViewModel;

/**
 * The View Model for the Display History View.
 */
public class DisplayHistoryViewModel extends ViewModel<DisplayHistoryState> {

    public DisplayHistoryViewModel(){
        super("displayHistoryView");
        setState(new DisplayHistoryState());
    }
}
