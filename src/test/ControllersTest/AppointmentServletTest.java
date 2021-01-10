package ControllersTest;

import Controllers.AppointmentServlet;
import jdk.nashorn.internal.ir.annotations.Ignore;
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

    long monday = 392580000; //
    long tuesday = 549018;
    long wednesday = 549018000; //
    long thursday = 54901800; //
    long friday = 1322018752;
    long saturday = 1322018752;
    long sunday = 1322018752;

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

    @Ignore
    @Test
    void Given_DeleteGetAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performGetAction("Delete", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_ViewGetAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performGetAction("View", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_UpdateGetAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performGetAction("Update", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_ConfirmPostAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("Confirm", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_AvailabilityPostAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("Availability", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_ViewSelectedPostAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("View Selected", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_ViewAllAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("View All", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_DeletePostAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("Delete Selected", mockedRequest);

        // Then
        assertEquals(result, mockedRequest.getRequestDispatcher("/appointments.jsp"));
    }

    @Ignore
    @Test
    void Given_UpdatePostAction_Then_SelectDispatcherCase() {
        // Given

        // When
        RequestDispatcher result = servlet.performPostAction("Update Selected", mockedRequest);

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
    void Given_TuesdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(tuesday));

        // Then
        assertEquals(result, "Tuesday");
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

    @Test
    void Given_FridayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(friday));

        // Then
        assertEquals(result, "Friday");
    }

    @Test
    void Given_SaturdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(saturday));

        // Then
        assertEquals(result, "Saturday");
    }

    @Test
    void Given_SundayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = servlet.getAvailableDay(new Date(sunday));

        // Then
        assertEquals(result, "Sunday");
    }
}