package interface_adapter.user_profile.setup;

import interface_adapter.ViewModel;

public class SetupViewModel extends ViewModel<SetupState> {
    public SetupViewModel() {
        super("setupView");
        setState(new SetupState());
    }
}
