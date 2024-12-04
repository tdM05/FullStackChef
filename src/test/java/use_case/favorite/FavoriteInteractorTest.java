package use_case.favorite;

import app.SessionUser;
import entity.CommonUser;
import entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteInteractorTest {
    @Test
    void executeFail() {
        SessionUser.getInstance().setUser(null);
        FavoriteOutputBoundary presenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Error with retrieving user", errorMessage);
            }
        };
        FavoriteDataAccessInterface dataAccess = new FavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                return null;
            }

            @Override
            public void saveFavorites(User user, List<Integer> favorites) {
            }
        };
        FavoriteInteractor favoriteInteractor = new FavoriteInteractor(
                dataAccess, presenter);
        FavoriteInputData favoriteInputData = new FavoriteInputData(1);
        favoriteInteractor.execute(favoriteInputData);
    }

    @Test
    void execute() {
        SessionUser.getInstance().setUser(new CommonUser("test", "test"));
        FavoriteOutputBoundary presenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) {
                assertTrue(outputData.getFavorite());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail();
            }
        };
        FavoriteDataAccessInterface dataAccess = new FavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                List<Integer> res = new ArrayList<>();
                res.add(0);
                return res;
            }

            @Override
            public void saveFavorites(User user, List<Integer> favorites) {
                // nothing to save
            }
        };
        FavoriteInteractor favoriteInteractor = new FavoriteInteractor(
                dataAccess, presenter);
        FavoriteInputData favoriteInputData = new FavoriteInputData(1);
        favoriteInteractor.execute(favoriteInputData);
    }

    @Test
    void execute2() {
        SessionUser.getInstance().setUser(new CommonUser("test", "test"));
        FavoriteOutputBoundary presenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) {
                assertFalse(outputData.getFavorite());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail();
            }
        };
        FavoriteDataAccessInterface dataAccess = new FavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                List<Integer> res = new ArrayList<>();
                res.add(0);
                return res;
            }

            @Override
            public void saveFavorites(User user, List<Integer> favorites) {
                // nothing to save
            }
        };
        FavoriteInteractor favoriteInteractor = new FavoriteInteractor(
                dataAccess, presenter);
        FavoriteInputData favoriteInputData = new FavoriteInputData(0);
        favoriteInteractor.execute(favoriteInputData);
    }
}