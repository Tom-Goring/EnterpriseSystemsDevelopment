package UtilsTest;

import Utils.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_NullDatabase_Then_ReturnDatabase() {
        // Given

        // When

        // Then
        assertNotNull(Database.getInstance());
    }
}