package ModelsTest.AppointmentTest;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.User.User;
import Utils.Tables;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static Models.Appointment.AppointmentDAO.formatSelectedSlot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class AppointmentDAOTest {

    User staff;
    User patient;
    Appointment appointment;

    @BeforeEach
    void setUp() {
        staff = mock(User.class);
        patient = mock(User.class);
        appointment = new Appointment(12, staff, patient, new Date(12), new Time(1), new Time(1), "Appointment");
    }

    @AfterEach
    void tearDown() {
        Tables.recreateTables();
    }

    @Ignore
    @Test
    void Given_InvalidAppointmentForInsert_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                SQLException.class,
                () -> AppointmentDAO.insertAppointment(appointment)
        );
    }

    @Ignore
    @Test
    void Given_InvalidAppointmentForUpdate_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                SQLException.class,
                () -> AppointmentDAO.updateAppointment(appointment)
        );
    }

    @Ignore
    @Test
    void Given_InvalidIDForDelete_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                SQLException.class,
                () -> AppointmentDAO.deleteAppointment(12345)
        );
    }

    @Ignore
    @Test
    void Given_InvalidDateForRetrieve_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                SQLException.class,
                () -> AppointmentDAO.retrieveAppointments(patient)
        );
    }

    @Test
    void Given_Slot_Then_FormatTime() {
        // Given
        Time[] time = new Time[2];
        time[0] = Time.valueOf("12:12" + ":00");
        time[1] = Time.valueOf("12:12" + ":00");

        // When
        Time[] result = formatSelectedSlot("12:12-12:12");
        long check = result[0].getTime();

        // Then
        assertEquals(check, time[0].getTime());
    }
}