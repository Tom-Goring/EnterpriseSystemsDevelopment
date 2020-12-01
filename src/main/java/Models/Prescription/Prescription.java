package Models.Prescription;

import Models.User.User;    

import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bredan
 */

public class Prescription {
    private final Integer patient;
    private final String medicine;
    private final Integer quantity;
    private final Boolean repeating;
    private final Date issueDate;
    private final Date endDate;
    
    public Prescription(Integer patientID, String medicine, Integer quantity, Boolean repeating, Date issueDate, Date endDate){
        this.patient = patientID;
        this.medicine = medicine;
        this.quantity = quantity;
        this.repeating = repeating;
        this.issueDate = issueDate;
        this.endDate = endDate;
    }
        
    public Integer getPatient() {
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
    
}
