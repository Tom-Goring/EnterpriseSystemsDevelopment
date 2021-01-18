package ControllersTest;

import Controllers.AppointmentServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AppointmentServletTest {

    HttpServletRequest mockedRequest;
    AppointmentServlet servlet;

    long monday = 392580000;
    long wednesday = 549018000;
    long thursday = 54901800;

    @BeforeEach
    void setUp() {
        servlet = new AppointmentServlet();
        mockedRequest = mock(HttpServletRequest.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_AddGetAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performGetAction("Add", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Test
    void Given_MondayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(monday));

        // Then
        assertEquals(result, "Monday");
    }

    @Test
    void Given_WednesdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(wednesday));

        // Then
        assertEquals(result, "Wednesday");
    }

    @Test
    void Given_ThursdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(thursday));

        // Then
        assertEquals(result, "Thursday");
    }
}