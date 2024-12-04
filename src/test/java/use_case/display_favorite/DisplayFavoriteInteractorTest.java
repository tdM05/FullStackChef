package use_case.display_favorite;

import app.SessionUser;
import entity.*;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class DisplayFavoriteInteractorTest {

    @Test
    void executeFail() {
        SessionUser.getInstance().setUser(null);
        DisplayFavoriteDataAccessInterface dataAccess = new DisplayFavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                return List.of();
            }

            @Override
            public List<Recipe> getRecipes(String recipeId) throws IOException, JSONException {
                return List.of();
            }
        };
        DisplayFavoriteOutputBoundary presenter = new DisplayFavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(List<DisplayFavoriteOutputData> outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Error with retrieving user", message);
            }
            @Override
            public void switchToSearchView() {
                fail();
            }
        };
        DisplayFavoriteInteractor displayFavoriteInteractor = new DisplayFavoriteInteractor(
                dataAccess, presenter);
        displayFavoriteInteractor.execute();

    }

    @Test
    void executeFailException() {
        SessionUser.getInstance().setUser(new CommonUser("test", "test"));
        DisplayFavoriteDataAccessInterface dataAccess = new DisplayFavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                return List.of();
            }

            @Override
            public List<Recipe> getRecipes(String recipeId) throws IOException, JSONException {
                throw new IOException();
            }
        };
        DisplayFavoriteOutputBoundary presenter = new DisplayFavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(List<DisplayFavoriteOutputData> outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Failed to get recipes.", message);
            }
            @Override
            public void switchToSearchView() {
                fail();
            }
        };
        DisplayFavoriteInteractor displayFavoriteInteractor = new DisplayFavoriteInteractor(
                dataAccess, presenter);
        displayFavoriteInteractor.execute();

    }

    @Test
    void execute() {
        SessionUser.getInstance().setUser(new CommonUser("test", "test"));
        DisplayFavoriteDataAccessInterface dataAccess = new DisplayFavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                List<Integer> res  = new ArrayList<>();
                res.add(1);
                return res;
            }

            @Override
            public List<Recipe> getRecipes(String recipeId) throws IOException, JSONException {
                List<Recipe> res  = new ArrayList<>();
                Ingredient ingredient = new CommonIngredient("test", 1f, "unit");
                Recipe recipe = new CommonRecipe(1,
                        "title",
                        null,
                        null,
                        null,
                        null,
                        null,
                        true
                        );
                res.add(recipe);
                return res;
            }
        };
        DisplayFavoriteOutputBoundary presenter = new DisplayFavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(List<DisplayFavoriteOutputData> outputData) {
                assertEquals(1, outputData.size());
            }

            @Override
            public void prepareFailView(String message) {
                fail();
            }
            @Override
            public void switchToSearchView() {
                fail();
            }
        };
        DisplayFavoriteInteractor displayFavoriteInteractor = new DisplayFavoriteInteractor(
                dataAccess, presenter);
        displayFavoriteInteractor.execute();

    }

    @Test
    void switchToSearchView() {
        DisplayFavoriteInteractor displayFavoriteInteractor = new DisplayFavoriteInteractor(
                new DisplayFavoriteDataAccessInterface() {
                    @Override
                    public List<Integer> getFavorites(User user) {
                        return List.of();
                    }

                    @Override
                    public List<Recipe> getRecipes(String recipeId) throws IOException, JSONException {
                        return List.of();
                    }
                },
                new DisplayFavoriteOutputBoundary() {
                    @Override
                    public void prepareSuccessView(List<DisplayFavoriteOutputData> outputData) {
                        fail();
                    }

                    @Override
                    public void prepareFailView(String message) {
                        fail();
                    }

                    @Override
                    public void switchToSearchView() {
                        assertTrue(true);
                    }
                }
        );
        displayFavoriteInteractor.switchToSearchView();
    }
}