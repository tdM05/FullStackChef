package use_case.dietaryrestrictions;

import app.SessionUser;
import entity.CommonDietaryRestriction;
import entity.CommonUser;
import interface_adapter.dietaryrestrictions.DietaryRestrictionDataAccessInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietaryRestrictionRequestDataTest {

    @Test
    void testGetSelectedDiets() {
        List<String> diets = List.of("vegan", "paleo");
        DietaryRestrictionRequestData requestData = new DietaryRestrictionRequestData(diets);
        assertEquals(diets, requestData.getSelectedDiets(), "Should return the diets passed to the constructor");
    }
}

class DietaryRestrictionResponseDataTest {

    @Test
    void testResponseData() {
        DietaryRestrictionResponseData responseData = new DietaryRestrictionResponseData("Message", List.of("vegan"));
        assertEquals("Message", responseData.getMessage());
        assertEquals(List.of("vegan"), responseData.getDietaryRestrictions());
    }
}



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

    // Test getDietaryRestrictions - success
    @Test
    void getDietaryRestrictionsSuccess() throws IOException {
        SessionUser.getInstance().setUser(new CommonUser("testUser", "testPass"));
        DietaryRestrictionDataAccessInterface dataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) {
                // not needed
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                return new CommonDietaryRestriction(List.of("vegan", "paleo"));
            }
        };
        DietaryRestrictionOutputBoundary presenter = new DietaryRestrictionOutputBoundary() {
            @Override
            public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
                // not needed here
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail here");
            }
        };

        DietaryRestrictionInteractor interactor = new DietaryRestrictionInteractor(presenter, dataAccess);
        CommonDietaryRestriction restrictions = interactor.getDietaryRestrictions();
        assertEquals(List.of("vegan", "paleo"), restrictions.getDiets());
    }

    // Test getDietaryRestrictions - IOException
    @Test
    void getDietaryRestrictionsIOException() {
        SessionUser.getInstance().setUser(new CommonUser("testUser", "testPass"));
        DietaryRestrictionDataAccessInterface dataAccess = new DietaryRestrictionDataAccessInterface() {
            @Override
            public void saveDietaryRestrictions(CommonDietaryRestriction commonDietaryRestriction) {
                // not needed
            }

            @Override
            public CommonDietaryRestriction loadDietaryRestrictions() throws IOException {
                throw new IOException("Failed to load");
            }
        };
        DietaryRestrictionOutputBoundary presenter = new DietaryRestrictionOutputBoundary() {
            @Override
            public void prepareSuccessView(DietaryRestrictionResponseData responseData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // not expected here
            }
        };

        DietaryRestrictionInteractor interactor = new DietaryRestrictionInteractor(presenter, dataAccess);
        assertThrows(IOException.class, interactor::getDietaryRestrictions);
    }
}
