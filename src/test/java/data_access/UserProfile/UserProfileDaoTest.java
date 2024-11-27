package data_access.UserProfile;

import entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserProfileDaoTest {
    UserProfileDao userProfileDao = new UserProfileDao();
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