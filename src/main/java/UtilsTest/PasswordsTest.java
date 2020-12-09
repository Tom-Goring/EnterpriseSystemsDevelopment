package UtilsTest;

import Utils.Passwords;
import Utils.Tuple;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

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
    void Should_ReturnTuple_Given_Password() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given

        // When
        Tuple<?, ?> actual = Passwords.createSaltAndHash(password);

        // Then
        assertNotNull(actual);
        assertTrue(actual instanceof Tuple);
    }

    @Ignore
    @Test
    void Should_ReturnTrue_Given_CorrectValues() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given
        byte[] salt = new byte[16];
        byte[] expectedHash = new byte[16];

        // When
        boolean actual = Passwords.equals(password, salt, expectedHash);

        // Then
        assertTrue(actual);
    }

    @Test
    void Should_ReturnFalse_Given_IncorrectValues() throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Given
        byte[] salt = new byte[16]; // Valueless salt
        byte[] expectedHash = new byte[16]; // Valueless hash

        // When
        boolean actual = Passwords.equals(password, salt, expectedHash);

        // Then
        assertFalse(actual);
    }
}