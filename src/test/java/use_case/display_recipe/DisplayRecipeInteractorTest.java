package use_case.display_recipe;

import entity.Recipe;
import entity.CommonRecipe;
import entity.CommonIngredient;
import entity.CommonNutritionalInfo;
import entity.CommonMeasurable;
import entity.CommonInstruction;
import entity.CommonEquipment;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class DisplayRecipeInteractorTest {

    private DisplayRecipeDataAccessInterface dataAccess;
    private DisplayRecipeOutputBoundary presenter;

    @Before
    public void setup() {
        // Mock data access
        dataAccess = new DisplayRecipeDataAccessInterface() {
            @Override
            public Recipe getRecipeById(int recipeId) throws IOException, JSONException {
                if (recipeId == 123) {
                    return new CommonRecipe(
                            123,
                            "Doughnuts",
                            "https://spoonacular.com/recipeImages/716276-312x231.jpg",
                            "jpg",
                            List.of(new CommonIngredient("Flour", 500, "grams")),
                            new CommonNutritionalInfo(
                                    new CommonMeasurable<>(100.0f, "kcal"),
                                    new CommonMeasurable<>(10.0f, "g"),
                                    new CommonMeasurable<>(5.0f, "g"),
                                    new CommonMeasurable<>(23.6f, "g"),
                                    new CommonMeasurable<>(2.0f, "g")
                            ),
                            List.of(
                                    new CommonInstruction(1,
                                            List.of(new CommonIngredient("Flour", 200, "grams")),
                                            List.of(new CommonEquipment(null, 0, null)),
                                            "Mix all the ingredients."
                                    ),
                                    new CommonInstruction(2,
                                            List.of(),
                                            List.of(new CommonEquipment(null, 0, null)),
                                            "Bake at 350°F for 15 minutes."
                                    )
                            ),
                            true
                    );
                } else if (recipeId == 404) {
                    throw new IOException("Recipe not found");
                } else if (recipeId == 500) {
                    throw new JSONException("Invalid recipe format");
                }
                return null;
            }
        };

        // Default presenter
        presenter = new DisplayRecipeOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayRecipeOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(123, outputData.getRecipeId());
                assertEquals("Doughnuts", outputData.getTitle());
                assertEquals("https://spoonacular.com/recipeImages/716276-312x231.jpg", outputData.getImage());
                assertEquals("jpg", outputData.getImageType());
                assertEquals(1, outputData.getIngredients().size());
                assertEquals("Flour", outputData.getIngredients().get(0).getName());
                assertTrue(outputData.isFavorite());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Failure view should not be called for a valid recipe");
            }

            @Override
            public void switchToSearchView() {
                System.out.println("Switching to search view");
            }
        };
    }

    @Test
    public void testExecuteWithValidRecipe() {
        DisplayRecipeInteractor interactor = new DisplayRecipeInteractor(dataAccess, presenter);
        DisplayRecipeInputData inputData = new DisplayRecipeInputData(123);
        interactor.execute(inputData);
    }

    @Test
    public void testExecuteWithIOException() {
        DisplayRecipeOutputBoundary ioExceptionPresenter = new DisplayRecipeOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayRecipeOutputData outputData) {
                fail("Success view should not be called for IOException");
            }

            @Override
            public void prepareFailView(String error) {
                assertNotNull(error);
                assertTrue(error.contains("Failed to retrieve recipe: Recipe not found"));
            }

            @Override
            public void switchToSearchView() {
                fail("Switch to search view should not be called here");
            }
        };

        DisplayRecipeInteractor interactor = new DisplayRecipeInteractor(dataAccess, ioExceptionPresenter);
        interactor.execute(new DisplayRecipeInputData(404));
    }

    @Test
    public void testExecuteWithJSONException() {
        DisplayRecipeOutputBoundary jsonExceptionPresenter = new DisplayRecipeOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayRecipeOutputData outputData) {
                fail("Success view should not be called for JSONException");
            }

            @Override
            public void prepareFailView(String error) {
                assertNotNull(error);
                assertTrue(error.contains("Failed to retrieve recipe: Invalid recipe format"));
            }

            @Override
            public void switchToSearchView() {
                fail("Switch to search view should not be called here");
            }
        };

        DisplayRecipeInteractor interactor = new DisplayRecipeInteractor(dataAccess, jsonExceptionPresenter);
        interactor.execute(new DisplayRecipeInputData(500));
    }

    @Test
    public void testSwitchToSearchView() {
        DisplayRecipeOutputBoundary searchViewPresenter = new DisplayRecipeOutputBoundary() {
            @Override
            public void prepareSuccessView(DisplayRecipeOutputData outputData) {
                fail("Success view should not be called");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Failure view should not be called");
            }

            @Override
            public void switchToSearchView() {
                System.out.println("Switching to search view");
            }
        };

        DisplayRecipeInteractor interactor = new DisplayRecipeInteractor(dataAccess, searchViewPresenter);
        interactor.switchToSearchView();
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteWithNullInput() {
        DisplayRecipeInteractor interactor = new DisplayRecipeInteractor(dataAccess, presenter);
        interactor.execute(null);
    }
}
