package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserTest {

    @Test
    void testForSetAndVerifyPassword() {
        User userForTest = new User();

        userForTest.setPassword("apple123");

        Assertions.assertTrue(userForTest.verifyPassword("apple123"));
        Assertions.assertFalse(userForTest.verifyPassword("apple124"));
    }

    @Test
    void testForGetAge() {
        LocalDate today = LocalDate.now();
        final int USER_YEAR_OF_BIRTH = 1990;

        User userForTest = new User();
        userForTest.setDateOfBirth(LocalDate.of(USER_YEAR_OF_BIRTH, 1, 1));

        LocalDate userForTestDateOfBirth = userForTest.getDateOfBirth();
        int expectedAge = today.getYear() - userForTest.getDateOfBirth().getYear();

        // If the user's birthday hasn't occurred yet this year, subtract one from the age
        if (userForTestDateOfBirth.isAfter(today.withYear(userForTestDateOfBirth.getYear()))) {
            expectedAge--;
        }

        Assertions.assertEquals(expectedAge, userForTest.getAge());
    }
}