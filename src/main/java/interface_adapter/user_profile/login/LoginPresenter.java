package interface_adapter.user_profile.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.user_profile.login.LoginState;
import interface_adapter.user_profile.login.LoginViewModel;
import use_case.user_profile.login.LoginOutputBoundary;
import use_case.user_profile.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final SearchViewModel searchViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,LoginViewModel loginViewModel,
                          SearchViewModel searchViewModel){
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.searchViewModel = searchViewModel;

    }

    @Override
    public void prepareSuccessView(LoginOutputData response){
        // On success, switch to the search view.
        final SearchState searchState = searchViewModel.getState();
        System.out.println(searchState);
        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(searchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error){
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }
}
