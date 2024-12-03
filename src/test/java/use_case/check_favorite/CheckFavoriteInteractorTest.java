package use_case.check_favorite;

import app.SessionUser;
import entity.CommonUser;
import entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckFavoriteInteractorTest {

    @Test
    void executeFailView() {
        // Here we don't log in a user
        SessionUser.getInstance().setUser(null);
        CheckFavoriteDataAccessInterface dataAccess = new CheckFavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                return List.of(1, 2, 3);
            }
        };
        CheckFavoriteOutputBoundary presenter = new CheckFavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(CheckFavoriteOutputData outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Error with retrieving user", message);
            }
        };
        CheckFavoriteInteractor checkFavoriteInteractor = new CheckFavoriteInteractor(
                dataAccess, presenter);
        checkFavoriteInteractor.execute(new CheckFavoriteInputData(1));
    }
    @Test
    void execute() {
        // Here we log in a user
        User user = new CommonUser("jonathan_calver2", "abc123");
        SessionUser.getInstance().setUser(user);
        CheckFavoriteDataAccessInterface dataAccess = new CheckFavoriteDataAccessInterface() {
            @Override
            public List<Integer> getFavorites(User user) {
                return List.of(1, 2, 3);
            }
        };
        CheckFavoriteOutputBoundary presenter = new CheckFavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(CheckFavoriteOutputData outputData) {
                assertTrue(outputData.getFavorite());
            }

            @Override
            public void prepareFailView(String message) {
                fail();
            }
        };
        CheckFavoriteInteractor checkFavoriteInteractor = new CheckFavoriteInteractor(
                dataAccess, presenter);
        checkFavoriteInteractor.execute(new CheckFavoriteInputData(1));
    }
}