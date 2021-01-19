package Models.Prescription;

import Models.User.User;
import java.sql.*;

/**
 *
 * @author Bredan
 */

public class Prescription {
    private final Integer ID;
    private final User patient;
    private final String medicine;
    private final Integer quantity;
    private final Boolean repeating;
    private final Date issueDate;
    private final Date endDate;
    
    public Prescription(Integer ID, User patient, String medicine, int quantity, Boolean repeating, Date issueDate, Date endDate){
        this.ID = ID;
        this.patient = patient;
        this.medicine = medicine;
        this.quantity = quantity;
        this.repeating = repeating;
        this.issueDate = issueDate;
        this.endDate = endDate;
    }

    public Integer getID() {
        return ID;
    }

    public User getPatient() {
        return patient;
    }

    public String getMedicine() {
        return medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getRepeating() {
        return repeating;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Prescription{" + "ID=" + ID + ", patient=" + patient + ", medicine=" + medicine + ", quantity=" + quantity + ", repeating=" + repeating + ", issueDate=" + issueDate + ", endDate=" + endDate + '}';
    }
    
}
