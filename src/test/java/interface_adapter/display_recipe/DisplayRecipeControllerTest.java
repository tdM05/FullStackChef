package interface_adapter.display_recipe;

import org.junit.Test;
import use_case.display_recipe.DisplayRecipeInputBoundary;
import use_case.display_recipe.DisplayRecipeInputData;

import static org.junit.Assert.*;

public class DisplayRecipeControllerTest {

    @Test
    public void execute() {
        DisplayRecipeController displayRecipeController = new DisplayRecipeController(new DisplayRecipeInputBoundary() {
            @Override
            public void execute(DisplayRecipeInputData inputData) {
                assertEquals(1, inputData.getRecipeId());
            }
        });
        displayRecipeController.execute(1);
    }
}