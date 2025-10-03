package UserManagement;

import Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserServiceTest {

    @Test
    void testForRegister() {
        UserService userServiceForTest = new UserService();

        User userForTest = new User();

        userForTest.setFirstName("Tim");
        userForTest.setLastName("Cook");
        userForTest.setUsername("timcook");
        userForTest.setPassword("apple123");
        userForTest.setHeightCm(180);
        userForTest.setWeightKg(75);
        userForTest.setDateOfBirth(LocalDate.of(1960, 11, 1));

        Assertions.assertTrue(userServiceForTest.register(userForTest));
    }

    @Test
    void testForRegisterNull() {
        UserService userServiceForTest = new UserService();

        User userForTest = null;

        Assertions.assertFalse(userServiceForTest.register(userForTest));
    }

    @Test
    void testForLogin() {
        UserService userServiceForTest = new UserService();

        User userForTest = new User();

        userForTest.setFirstName("Tim");
        userForTest.setLastName("Cook");
        userForTest.setUsername("timcook");
        userForTest.setPassword("apple123");
        userForTest.setHeightCm(180);
        userForTest.setWeightKg(75);
        userForTest.setDateOfBirth(LocalDate.of(1960, 11, 1));

        userServiceForTest.register(userForTest);

        User loggedInUser = userServiceForTest.login("timcook", "apple123");
        Assertions.assertEquals(userForTest, loggedInUser);
    }

    @Test
    void testForLogout() {
        UserService userServiceForTest = new UserService();

        User userForTest = new User();

        userForTest.setFirstName("Tim");
        userForTest.setLastName("Cook");
        userForTest.setUsername("timcook");
        userForTest.setPassword("apple123");
        userForTest.setHeightCm(180);
        userForTest.setWeightKg(75);
        userForTest.setDateOfBirth(LocalDate.of(1960, 11, 1));

        userServiceForTest.register(userForTest);

        User loggedInUser = userServiceForTest.login("timcook", "apple123");
        Assertions.assertEquals(userForTest, loggedInUser);

        userServiceForTest.logout();
        Assertions.assertNull(userServiceForTest.getLoggedInUser());
    }

    @Test
    void testForUsernameExists() {
        UserService userServiceForTest = new UserService();

        User userForTest = new User();
        userForTest.setUsername("timcook");

        userServiceForTest.register(userForTest);

        Assertions.assertTrue(userServiceForTest.usernameExists("timcook"));
    }
}