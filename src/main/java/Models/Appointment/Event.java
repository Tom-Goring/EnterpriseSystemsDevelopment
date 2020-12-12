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
    private final int ID;
    private final String doctorName;
    private final String date;
    private final String startTime;
    private final String endTime;
    private final String customerName;
    private final String type;
    private final String email;

    public Event(int ID, String doctorName, String date, String startTime, String endTime, String customerName, String type,String email) {
        this.ID = ID;
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.type = type;
        this.email = email;
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
    