package view.user_profile;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class BaseView extends JPanel implements PropertyChangeListener {
    protected final String viewName;
    protected final ViewManagerModel viewManagerModel;

    /**
     * Constructor for the base view.
     *
     * @param viewName          the name of the view.
     * @param viewManagerModel  the view manager model for navigation.
     */
    public BaseView(String viewName, ViewManagerModel viewManagerModel) {
        this.viewName = viewName;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof ViewManagerState) {
            final ViewManagerState state = (ViewManagerState) evt.getNewValue();
            if (state.getViewName().equals(viewName)) {
                refresh();
            }
        }
    }

    /**
     * Refreshes the view with updated content.
     */
    protected abstract void refresh();
}