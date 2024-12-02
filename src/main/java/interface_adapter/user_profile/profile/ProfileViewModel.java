package interface_adapter.user_profile.profile;

public class ProfileViewModel {
    private final ProfileState state;

    public ProfileViewModel() {
        this.state = new ProfileState();
    }

    public ProfileState getState() {
        return state;
    }
}
