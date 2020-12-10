package ModelsTest.UserTest;

import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserDao;
import Models.User.UserNotFoundException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDaoTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() throws IOException {
        outputStreamCaptor.close();
        // TODO: Revert changes made to database in unit test 'Given_ValidUser_Then_InsertUser'
    }

    @Ignore
    @Test
    void Given_ValidUser_Then_InsertUser() throws DuplicateEmailPresentException {
        // Given
        User user = new User(12, "Charlie", "Williams,", "charlie@uwe.ac.uk", new byte[16], new byte[16]);

        // When
        UserDao.insertUser(user);

        // Then
        assertEquals("User inserted", outputStreamCaptor.toString());
    }

    @Ignore
    @Test
    void Given_DuplicateUser_Then_ThrowException() {
        // Given
        User user = new User(12, "Charlie", "Williams,", "charlie@uwe.ac.uk", new byte[16], new byte[16]);


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
        String email = "charlie@uwe.ac.uk";

        // When
        User actual = UserDao.getUserByEmail(email);

        // Then
        assertNotNull(actual);
    }

    @Ignore
    @Test
    void Given_IncorrectEmail_Then_ThrowException() {
        // Given
        String email = "charlie@uwe.ac.uk";

        // When


        // Then
        assertThrows(
                UserNotFoundException.class,
                () -> UserDao.getUserByEmail(email)
        );
    }
}