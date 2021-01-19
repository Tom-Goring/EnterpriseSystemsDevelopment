package ControllersTest;

import Controllers.IssuePrescriptionServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IssuePrescriptionServletTest {

    IssuePrescriptionServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new IssuePrescriptionServlet();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_ServletInfoExecuted_Then_ReturnDescription() {
        // Given

        // When
        String result = servlet.getServletInfo();

        // Then
        assertEquals(result, "Short description");
    }
}