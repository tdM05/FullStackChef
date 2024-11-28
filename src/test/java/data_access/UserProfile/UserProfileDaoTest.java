package data_access.UserProfile;

import entity.User;
import org.junit.Test;
import use_case.login.LoginInteractorTest;

import java.util.List;

import static org.junit.Assert.*;

public class UserProfileDaoTest {
    UserProfileDao userProfileDao = new UserProfileDao();
//    @Test
//    public void setMeals() {
//        // First we login
//        LoginInteractorTest loginInteractorTest = new LoginInteractorTest();
//        loginInteractorTest.LoginWithTestUser();
//
//        // now our actual test
//        List<Integer> mealIds = List.of(1, 2, 3, 4);
//        userProfileDao.setMeals(mealIds);
//    }
    @Test
    public void existsByName() {
        boolean res = userProfileDao.existsByName("test_user");
        assertTrue(res);
    }
    @Test
    public void getUser() {
        User user = userProfileDao.getUser("test_user");
    }
}