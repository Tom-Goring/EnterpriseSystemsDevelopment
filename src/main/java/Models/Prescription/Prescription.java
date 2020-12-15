package Models.Prescription;

/**
 *
 * @author Bredan
 */

public class Prescription {
    private final Integer patientID;
    private final String medicine;
    private final Integer quantity;
    private final Boolean repeating;
    private final String issueDate;
    private final String endDate;
    
    public Prescription(Integer patientID, String medicine, Integer quantity, Boolean repeating, String issueDate, String endDate){
        this.patientID = patientID;
        this.medicine = medicine;
        this.quantity = quantity;
        this.repeating = repeating;
        this.issueDate = issueDate;
        this.endDate = endDate;
    }
    
    public Integer getPatientID() {
        return patientID;
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

    public String getIssueDate() {
        return issueDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
