package use_case.dietaryrestrictions;

import app.SessionUser;
import entity.CommonDietaryRestriction;
import entity.CommonUser;
import entity.User;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietaryRestrictionInteractorTest {

    @Test
    void executeFailNoUser() {
        // Simulate no user is logged in
        SessionUser.getInstance().setUser(null);

        DietaryRestrictionOutputBoundary presenter = new DietaryRestrictionOutputBoundary() {
            @Override
            public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
                fail("Should not succeed when no user is logged in");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Failed to update dietary restrictions: No user is logged in. Please log in to continue.", errorMessage);
            }
        };

        DietaryRestrictionDataAccessInterface dataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
                // Should not be called
                fail("No user logged in, should not save");
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                return new CommonDietaryRestriction(List.of());
            }
        };

        DietaryRestrictionInteractor interactor = new DietaryRestrictionInteractor(presenter, dataAccess);
        DietaryRestrictionRequestData requestData = new DietaryRestrictionRequestData(List.of("vegan", "paleo"));
        interactor.execute(requestData);
    }

    @Test
    void executeSuccess() {
        // Simulate a logged-in user
        SessionUser.getInstance().setUser(new CommonUser("testUser", "testPass"));

        DietaryRestrictionOutputBoundary presenter = new DietaryRestrictionOutputBoundary() {
            @Override
            public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
                assertEquals("Dietary restrictions saved successfully.", responseData.getMessage());
                assertTrue(responseData.getDietaryRestrictions().contains("vegan"));
                assertTrue(responseData.getDietaryRestrictions().contains("paleo"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail when user is logged in and data saves successfully");
            }
        };

        DietaryRestrictionDataAccessInterface dataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
                // Simulate successful save
                assertTrue(commonDietaryRestriction.getDiets().contains("vegan"));
                assertTrue(commonDietaryRestriction.getDiets().contains("paleo"));
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                // Return some existing restrictions
                return new CommonDietaryRestriction(List.of("vegan"));
            }
        };

        DietaryRestrictionInteractor interactor = new DietaryRestrictionInteractor(presenter, dataAccess);
        DietaryRestrictionRequestData requestData = new DietaryRestrictionRequestData(List.of("vegan", "paleo"));
        interactor.execute(requestData);
    }

    @Test
    void executeFailOnDataAccess() {
        // Simulate a logged-in user
        SessionUser.getInstance().setUser(new CommonUser("testUser", "testPass"));

        DietaryRestrictionOutputBoundary presenter = new DietaryRestrictionOutputBoundary() {
            @Override
            public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
                fail("Should not succeed when data access fails");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.startsWith("Failed to update dietary restrictions: Simulated data access error"));
            }
        };

        DietaryRestrictionDataAccessInterface dataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) throws IOException {
                throw new IOException("Simulated data access error");
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                return new CommonDietaryRestriction(List.of());
            }
        };

        DietaryRestrictionInteractor interactor = new DietaryRestrictionInteractor(presenter, dataAccess);
        DietaryRestrictionRequestData requestData = new DietaryRestrictionRequestData(List.of("vegan"));
        interactor.execute(requestData);
    }
}
