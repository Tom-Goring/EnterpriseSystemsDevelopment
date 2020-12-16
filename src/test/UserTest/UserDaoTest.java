package UserTest;

import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserDao;
import Models.User.UserNotFoundException;
import Utils.Tables;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoTest {

    String email;

    @BeforeEach
    void setUp() {
        email = "charlie@uwe.ac.uk";
    }

    @AfterEach
    void tearDown() {
        Tables.recreateTables();
    }

    @Ignore
    @Test
    void Given_ValidUser_Then_InsertUser() throws DuplicateEmailPresentException {
        // Given
        User user = new User(12, "Charlie", "Williams,", email, new byte[16], new byte[16], "admin");

        // When
        boolean isInserted = UserDao.insertUser(user);

        // Then
        assertTrue(isInserted);
    }

    @Ignore
    @Test
    void Given_DuplicateUser_Then_ThrowException() throws DuplicateEmailPresentException {
        // Given
        User user = new User(12, "Charlie", "Williams,", email, new byte[16], new byte[16], "admin");
        UserDao.insertUser(user);

        // When

        // Then
        assertThrows(
                DuplicateEmailPresentException.class,
                () -> UserDao.insertUser(user)
        );
    }

    @Ignore
    @Test
    void Given_CorrectEmail_Then_ReturnUser() throws UserNotFoundException {
        // Given

        // When
        User actual = UserDao.getUserByEmail(email);

        // Then
        assertNotNull(actual);
    }

    @Ignore
    @Test
    void Given_IncorrectEmail_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                UserNotFoundException.class,
                () -> UserDao.getUserByEmail(email)
        );
    }
}