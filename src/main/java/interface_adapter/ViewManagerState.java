package interface_adapter;

/**
 * The state of the View Manager. It contains the name of the view
 * and the context of the view (which could be for example the id).
 */
public class ViewManagerState {
    private String viewName;
    private Object context;

    // Constructor
    public ViewManagerState(String viewName, Object context) {
        this.viewName = viewName;
        this.context = context;
    }

    public ViewManagerState() {

    }

    // Getter for viewName
    public String getViewName() {
        return viewName;
    }

    // Setter for viewName
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    // Getter for context
    public Object getContext() {
        return context;
    }

    // Setter for context
    public void setContext(Object context) {
        this.context = context;
    }
}
