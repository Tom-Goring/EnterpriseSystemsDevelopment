package UtilsTest;

import Utils.Passwords;
import Utils.Tuple;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordsTest {

    String password;

    @BeforeEach
    void setUp() {
        password = "Password123";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_Password_Then_ReturnTuple() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given

        // When
        Tuple<?, ?> actual = Passwords.createSaltAndHash(password);

        // Then
        assertNotNull(actual);
        assertTrue(actual instanceof Tuple);
    }

    @Ignore
    @Test
    void Given_CorrectValues_Then_ReturnTrue() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given
        byte[] salt = new byte[16];
        byte[] expectedHash = new byte[16];

        // When
        boolean actual = Passwords.equals(password, salt, expectedHash);

        // Then
        assertTrue(actual);
    }

    @Test
    void Given_IncorrectValues_Then_ReturnFalse() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given
        byte[] salt = new byte[16]; // Valueless salt
        byte[] expectedHash = new byte[16]; // Valueless hash

        // When
        boolean actual = Passwords.equals(password, salt, expectedHash);

        // Then
        assertFalse(actual);
    }
}