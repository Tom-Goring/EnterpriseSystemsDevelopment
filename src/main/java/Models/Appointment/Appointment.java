/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Appointment;

import Models.Schedule.Schedule;
import Models.User.User;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;

/**
 *
 * @author maxwell
 */
public class Appointment {
    private final Integer ID;
    private final User staffMember;
    private final User patient;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private String type;

    public Appointment(Integer ID, User staffMember, User patient, Date date, Time startTime, Time endTime, String type) {
        this.ID = ID;
        this.staffMember = staffMember;
        this.patient = patient;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public User getStaffMember() {
        return staffMember;
    }

    public User getPatient() {
        return patient;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Duration getLength() {
        LocalTime from = this.startTime.toLocalTime();
        LocalTime to = this.endTime.toLocalTime();

        return Duration.between(from, to);
    }

    public static String getDayOfDate(Date date) {
        Schedule sch = new Schedule();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return sch.getDayOfWeek(day);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "ID=" + ID +
                ", staffMember=" + staffMember +
                ", patient=" + patient +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
    