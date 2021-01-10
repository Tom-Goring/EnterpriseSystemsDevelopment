package ModelsTest.AppointmentTest;

import Models.Appointment.AppointmentDAO;
import Models.User.User;
import Utils.Tables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentDAOTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        Tables.recreateTables();
    }

    @Test
    void Given_ValidAppointment_Then_InsertAppointment() {
        // Given
        User staff = new User(12, "", "", "", "Doctor"); // TODO: Continue setup
        User patient = new User(13, "", "", "", "Patient");

        // When

        // Then
    }

    @Test
    void Given_DuplicateAppointmentForInsert_Then_ThrowException() {
        // Given

        // When

        // Then

    }

    @Test
    void Given_InvalidAppointmentForUpdate_Then_ThrowException() {
        // Given

        // When

        // Then

    }

    @Test
    void Given_InvalidAppointmentForDelete_Then_ThrowException() {
        // Given

        // When

        // Then

    }

    @Test
    void Given_InvalidDateForRetrieve_Then_ThrowException() {
        // Given

        // When

        // Then

    }

    @Test
    void Given_Slot_Then_FormatTime() {
        // Given

        // When
        Time[] result = AppointmentDAO.formatSelectedSlot("1");

        // Then
        assertEquals(result, new Time[3]); // TODO: Temp
    }
}