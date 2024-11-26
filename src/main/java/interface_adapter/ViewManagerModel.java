package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewManagerModel handles switching between different views in the application.
 */
public class ViewManagerModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ViewManagerState currentState;

    /**
     * Registers a view with its corresponding switch action.
     *
     * @param viewName     the name of the view
     * @param switchAction the action to switch to the view
     */
    public void registerView(String viewName, Runnable switchAction) {
        support.addPropertyChangeListener(viewName, evt -> switchAction.run());
    }

    /**
     * Sets the current state of the view manager.
     *
     * @param state the new state to set
     */
    public void setState(ViewManagerState state) {
        ViewManagerState oldState = this.currentState;
        this.currentState = state;
        support.firePropertyChange("state", oldState, state);
    }

    /**
     * Gets the current state of the view manager.
     *
     * @return the current state
     */
    public ViewManagerState getState() {
        return this.currentState;
    }

    /**
     * Fires a property changed event indicating the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.currentState);
    }

    /**
     * Adds a PropertyChangeListener.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener.
     *
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Sets the context for the current state.
     *
     * @param context the context to set
     */
    public void setContext(Object context) {
        if (this.currentState != null) {
            this.currentState.setContext(context);
            support.firePropertyChange("state", this.currentState, this.currentState);
        }
    }
}
