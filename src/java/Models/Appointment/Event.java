/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Appointment;

/**
 *
 * @author maxwell
 */
public class Event {
    private String doctorName;
    private String date;
    private String startTime;
    private String endTime;
    private String customerName;
    private String type;
    private String email;
    private int ID;
    
     public Event(int ID,String doctorName, String date, String startTime, String endTime, String customerName, String type,String email) {
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.type = type;
        this.email = email;
        this.ID = ID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }
    public int getID() {
        return ID;
    }
    
    public String getEmail() {
        return email;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Event{" + "doctorName=" + doctorName + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", customerName=" + customerName + ", type=" + type + '}';
    }
}
    
