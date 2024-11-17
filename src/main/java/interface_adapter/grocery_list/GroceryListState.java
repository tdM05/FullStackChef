package interface_adapter.grocery_list;

import java.util.List;

/**
 * The state of the Grocery List.
 */
public class GroceryListState {
    private List<String> list;
    private String error;

    /*
     * Each item in the list corresponds to a line in the view
     */
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
