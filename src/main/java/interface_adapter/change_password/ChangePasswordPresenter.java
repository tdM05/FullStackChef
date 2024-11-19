package interface_adapter.change_password;

import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ChangePasswordPresenter implements ChangePasswordOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public ChangePasswordPresenter(LoggedInViewModel loggedInViewModel){
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChangePasswordOutputData changePasswordOutputData){
        loggedInViewModel.firePropertyChanged("password");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // use case can't fail
    }
}
