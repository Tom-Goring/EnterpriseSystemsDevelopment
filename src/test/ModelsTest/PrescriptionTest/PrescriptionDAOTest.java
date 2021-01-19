package ModelsTest.PrescriptionTest;

import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.Prescription.PrescriptionNotFoundException;
import Utils.Tables;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrescriptionDAOTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        Tables.recreateTables();
    }

    @Ignore
    @Test
    void Given_InvalidPrescription_Then_ReturnTrue() {
        // Given
        Prescription prescription = new Prescription(99999, 1, "Calpol", 1, true, new Date(12), new Date(12));

        // When
        boolean result = PrescriptionDAO.insertPrescription(prescription);

        // Then
        assertFalse(result);
    }

    @Ignore
    @Test
    void Given_InvalidID_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                PrescriptionNotFoundException.class,
                () -> PrescriptionDAO.getPrescription(9999)
        );
    }

    @Ignore
    @Test
    void Given_InvalidUser_Then_ThrowException() {
        // Given

        // When

        // Then
        assertThrows(
                SQLException.class,
                () -> PrescriptionDAO.getAllPrescriptionsForUser(9999)
        );
    }
}