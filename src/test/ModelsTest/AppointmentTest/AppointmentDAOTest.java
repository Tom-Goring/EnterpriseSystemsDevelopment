package ModelsTest.AppointmentTest;

import Models.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentDAOTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void Given_ValidAppointment_Then_InsertAppointment() {
        // Given
        User staff = new User(12, "", "", "", "Doctor");
        User patient = new User(13, "", "", "", "Patient");

        // When

        // Then
    }
}