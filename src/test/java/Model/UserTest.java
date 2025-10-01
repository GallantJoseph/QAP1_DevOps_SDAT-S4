package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testForSetAndVerifyPassword() {
        User userForTest = new User();

        userForTest.setPassword("apple123");

        assertTrue(userForTest.verifyPassword("apple123"));
    }
}