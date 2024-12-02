package interface_adapter.user_profile.change_display_name;

public class ChangeDisplayNameViewModel {
    private final ChangeDisplayNameState state;

    public ChangeDisplayNameViewModel() {
        this.state = new ChangeDisplayNameState();
    }

    public ChangeDisplayNameState getState() {
        return state;
    }
}
