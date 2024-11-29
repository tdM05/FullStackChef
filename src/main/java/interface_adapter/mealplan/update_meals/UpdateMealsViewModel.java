package interface_adapter.mealplan.update_meals;

import interface_adapter.ViewModel;


public class UpdateMealsViewModel extends ViewModel<UpdateMealsState> {
    public UpdateMealsViewModel() {
        super("update meals");
        setState(new UpdateMealsState());
    }
}
