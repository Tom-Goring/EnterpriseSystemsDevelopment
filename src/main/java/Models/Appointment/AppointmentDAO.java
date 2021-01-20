/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Appointment;

import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;
import Utils.Database;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author maxwell
 */
public class AppointmentDAO {

    public static void insertAppointment(Appointment appointment) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement prep = con.prepareStatement("INSERT INTO appointments (patientID, staffID, date, startTime, endTime, type) VALUES (?, ?, ?, ?, ?, ?)");

            prep.setInt(1, appointment.getPatient().getID());
            prep.setInt(2, appointment.getStaffMember().getID());
            prep.setDate(3, appointment.getDate());
            prep.setTime(4, appointment.getStartTime());
            prep.setTime(5, appointment.getEndTime());
            prep.setString(6, appointment.getType());

            prep.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
            try {
                throw e;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void updateAppointment(Appointment appointment) {
        Connection con = Database.getInstance().getConnection();
        try {
            String sql = "UPDATE APPOINTMENTS SET type = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, appointment.getType());
            ps.setInt(2, appointment.getID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                throw ex;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void deleteAppointment(int ID) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM APPOINTMENTS WHERE ID = ?");
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                throw ex;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // TODO: merge retrieveStaff and retrievePatient

    public static ArrayList<Appointment> retrieveAppointments(User user) {
        try {
            ArrayList<Appointment> appointments = new ArrayList<>();
            Connection con = Database.getInstance().getConnection();
            String sql;
            if (user.isMedicalStaff()) {
                sql = "SELECT * FROM appointments WHERE STAFFID = ?";
            } else {
                sql = "SELECT * FROM appointments WHERE PATIENTID = ?";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getID());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("ID"),
                        UserDAO.getUser(rs.getInt("staffID")),
                        UserDAO.getUser(rs.getInt("patientID")),
                        rs.getDate("date"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("type")
                ));
            }
            return appointments;
        } catch (UserNotFoundException | SQLException e) {
            e.printStackTrace();
            try {
                throw e;
            } catch (SQLException | UserNotFoundException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
    }

    public static Appointment getAppointmentByID(Integer ID) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Appointment(
                    rs.getInt("ID"),
                    UserDAO.getUser(rs.getInt("staffID")),
                    UserDAO.getUser(rs.getInt("patientID")),
                    rs.getDate("date"),
                    rs.getTime("startTime"),
                    rs.getTime("endTime"),
                    rs.getString("type")
            );
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Appointment> retrieveAppointments() throws SQLException, UserNotFoundException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            new Appointment(
                    rs.getInt("ID"),
                    UserDAO.getUser(rs.getInt("staffID")),
                    UserDAO.getUser(rs.getInt("patientID")),
                    rs.getDate("date"),
                    rs.getTime("startTime"),
                    rs.getTime("endTime"),
                    rs.getString("type")
            );
        }
        return appointments;
    }

    public static ArrayList<Appointment> retrieveByDate(User staffMember, Date date) throws SQLException, UserNotFoundException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments WHERE STAFFID = ? AND DATE = ?");
        ps.setInt(1, staffMember.getID());
        ps.setDate(2, date);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getInt("ID"),
                    UserDAO.getUser(rs.getInt("staffID")),
                    UserDAO.getUser(rs.getInt("patientID")),
                    rs.getDate("date"),
                    rs.getTime("startTime"),
                    rs.getTime("endTime"),
                    rs.getString("type")
            ));
        }
        return appointments;
    }

    public static ArrayList<Appointment> retrieveAllBetweenDates(Date first, Date second) {
        try {
            ArrayList<Appointment> appointments = new ArrayList<>();
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments WHERE DATE > ? AND DATE < ?");
            ps.setDate(1, first);
            ps.setDate(2, second);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("ID"),
                        UserDAO.getUser(rs.getInt("staffID")),
                        UserDAO.getUser(rs.getInt("patientID")),
                        rs.getDate("date"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("type")
                ));
            }
            return appointments;
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getGeneratedSlots(int appointmentLength, User staffMember, Date date) {
        ArrayList<Appointment> appointments;
        ArrayList<String> intervals = new ArrayList<>();
        try {
            Time[] hours = new Time[2];
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            appointments = retrieveByDate(staffMember, date);

            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(timeFormat.parse("9:00"));
            cal2.setTime(timeFormat.parse("18:00"));
            hours[0] = new Time(cal.getTimeInMillis());

            while (cal.getTime().before(cal2.getTime())) {
                cal.add(Calendar.MINUTE, appointmentLength);
                Time startTime = new java.sql.Time(cal.getTimeInMillis());
                hours[1] = startTime;
                if (isAvailableSlot(hours, appointments)) {
                    String hoursRange = timeFormat.format(hours[0]) + '-' + timeFormat.format(hours[1]);
                    intervals.add(hoursRange);
                }
                Time temp = hours[1];
                hours[0] = temp;
            }
        } catch (ParseException | SQLException | UserNotFoundException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        }
        return intervals;
    }

    private static boolean isAvailableSlot(Time[] time, ArrayList<Appointment> currentAppointments) {
        Time slotStart = time[0];
        Time slotEnd = time[1];
        for (Appointment currentAppointment : currentAppointments) {
            Time appointmentStart = currentAppointment.getStartTime();
            Time appointmentEnd = currentAppointment.getEndTime();

            int a = slotStart.compareTo(appointmentStart);
            int b = slotStart.compareTo(appointmentEnd);

            if (slotStart.compareTo(appointmentStart) >= 0 && slotEnd.compareTo(appointmentEnd) <= 0) { // appointment that starts and ends in the middle of another appointment
                return false;
            } else if (slotStart.compareTo(appointmentStart) >= 0 && slotStart.before(appointmentEnd)) { // appointment that starts in the middle of another appointment
                return false;
            } else if (slotStart.before(appointmentStart) && slotEnd.after(appointmentStart) && slotEnd.compareTo(appointmentEnd) <= 0) { // appointment that starts before another and ends in the middle
                return false;
            }
        }
        return true;
    }

    public static Time[] formatSelectedSlot(String slot) {
        Time[] time = new Time[2];
        String[] timesAsStrings = slot.split("-");
        time[0] = Time.valueOf(timesAsStrings[0] + ":00");
        time[1] = Time.valueOf(timesAsStrings[1] + ":00");
        return time;
    }
}