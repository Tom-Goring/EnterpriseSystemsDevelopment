package ControllersTest;

import Models.Appointment.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentServletTest {

    long monday = 392580000;
    long wednesday = 549018000;
    long thursday = 54901800;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_MondayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = Appointment.getDayOfDate(new Date(monday));

        // Then
        assertEquals(result, "Monday");
    }

    @Test
    void Given_WednesdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = Appointment.getDayOfDate(new Date(wednesday));

        // Then
        assertEquals(result, "Wednesday");
    }

    @Test
    void Given_ThursdayDate_Then_ReturnDayOfWeek() {
        // Given

        // When
        String result = Appointment.getDayOfDate(new Date(thursday));

        // Then
        assertEquals(result, "Thursday");
    }
}