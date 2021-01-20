package ModelsTest.UserTest;

import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserAccount;
import Models.User.UserAccountDAO;
import Models.User.UserNotFoundException;
import Utils.Tables;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class UserDaoTest {

    String email;
    UserAccount user;

    @BeforeEach
    void setUp() {
        email = "charlie@uwe.ac.uk";
        user = mock(UserAccount.class);
    }

    @AfterEach
    void tearDown() {
        Tables.recreateTables();
    }

    @Ignore
    @Test
    void Given_ValidUser_Then_InsertUser() throws DuplicateEmailPresentException {
        // Given

        // When
        boolean isInserted = UserAccountDAO.insertUserAccount(user);

        // Then
        assertTrue(isInserted);
    }

    @Ignore
    @Test
    void Given_DuplicateUser_Then_ThrowException() throws DuplicateEmailPresentException {
        // Given
        UserAccountDAO.insertUserAccount(user);

        // When

        // Then
        assertThrows(
                DuplicateEmailPresentException.class,
                () -> UserAccountDAO.insertUserAccount(user)
        );
    }

    @Ignore
    @Test
    void Given_CorrectEmail_Then_ReturnUser() throws UserNotFoundException {
        // Given

        // When
        User actual = UserAccountDAO.getUserAccountByEmail(email);

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
                () -> UserAccountDAO.getUserAccountByEmail(email)
        );
    }
}