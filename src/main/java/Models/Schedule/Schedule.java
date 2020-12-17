/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Schedule;

import java.util.Arrays;

/**
 *
 * @author maxwell
 */
public class Schedule {

    private boolean [] schedule;
    private Integer staffID;

    public Schedule() {
        this.schedule = new boolean[7];
    }

    public Schedule(Integer staffID, boolean Monday, boolean Tuesday, boolean Wednesday, boolean Thursday, boolean Friday,boolean Saturday, boolean Sunday) {
        schedule = new boolean[7];
        this.staffID = staffID;
        schedule[0] = Monday;
        schedule[1] = Tuesday;
        schedule[2] = Wednesday;
        schedule[3] = Thursday;
        schedule[4] = Friday;
        schedule[5] = Saturday;
        schedule[6] = Sunday;      
    }

    public boolean getDayBoolean(int day) {
        return schedule[day];
    }

    public void setDayOfWeek(int day, boolean working) {
        this.schedule[day] = working;
    }

    public String getDayOfWeek(int day) {
        switch(day)
        {
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";            
        }
        return "";
    }

    public boolean getScheduleBoolean(int day) {
        return schedule[day];
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getStaffID() {
        return staffID;
    }

    @Override
    public String toString() {
        return "Schedule{" + "schedule=" + Arrays.toString(schedule) + ", staffID=" + staffID + '}';
    }
    
}

