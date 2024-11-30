package use_case.favorite;

import data_access.FavoriteDataAccessObject;
import entity.CommonUser;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteInteractorTest {

    private FavoriteDataAccessInterface dataAccess = new FavoriteDataAccessObject();
    private FavoriteOutputBoundary presenter;
    private FavoriteInputData inputData;
    private FavoriteInteractor interactor;
    private User testUser;

    @BeforeEach
    void setup () {
        testUser = new CommonUser("jonathan_calver2", "abc123");
        List<Integer> favorites = List.of(2, 3, 4);
        dataAccess.saveFavorites(testUser, favorites);
        }

    @Test
    void execute_addFavorite() {
        int recipeId = 1;
        FavoriteInputData inputData = new FavoriteInputData(recipeId);

        System.out.println(dataAccess.getFavorites(testUser));

    }

}