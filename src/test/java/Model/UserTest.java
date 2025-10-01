package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testForSetAndVerifyPassword() {
        User userForTest = new User();

        userForTest.setPassword("apple123");

        assertTrue(userForTest.verifyPassword("apple123"));
    }

    @Test
    void testForGetAge() {
        final int USER_YEAR_OF_BIRTH = 1990;

        User userForTest = new User();

        userForTest.setDateOfBirth(LocalDate.of(USER_YEAR_OF_BIRTH, 1, 1));

        LocalDate now = LocalDate.now();

        int expectedAge = now.getYear() - userForTest.getDateOfBirth().getYear();

        Assertions.assertEquals(expectedAge, userForTest.getAge());
    }
}