/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Appointment;

import Utils.Database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxwell
 */
public class AppointmentDAO {

    public static void insertAppointment(Event event) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement prep = con.prepareStatement("INSERT INTO appointments (customerID, doctorID, date, startTime, endTime, type) VALUES (?, ?, ?, ?, ?, ?)");

            LocalTime s = LocalTime.parse(event.getStartTime());
            LocalTime e = LocalTime.parse(event.getEndTime());
            Time start = Time.valueOf(s);
            Time end = Time.valueOf(e);
            java.sql.Date date = java.sql.Date.valueOf(event.getDate());
            String docName = event.getDoctorName();
            String custName = event.getCustomerName();
            String type = event.getType();
            String email = event.getEmail();

            prep.setString(1, custName);
            prep.setString(2, docName);
            prep.setDate(3, date);
            prep.setTime(4, start);
            prep.setTime(5, end);
            prep.setString(6, type);

            prep.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void updateAppointment(int id, String customerName, String type, String email) {
        Connection con = Database.getInstance().getConnection();
        try {
            String sql = "UPDATE APPOINTMENTS SET CUSTOMERNAME=?, CUSTOMEREMAIL=?, APPTMTTYPE =? WHERE ID =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, email);
            ps.setString(3, type);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteAppointment(int id) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM APPOINTMENTS WHERE ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> retrieveDoctors() {
        ArrayList<String> doctors = new ArrayList<>();
        try {
            Connection con = Database.getInstance().getConnection();
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String role = rs.getString("role");
                if ("doctor".equals(role)) {
                    doctors.add(rs.getString("FIRSTNAME"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        }
        return doctors;
    }

    public static ArrayList<Event> retrieveDoctorsAppointment(String name, boolean singleSelection) throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        Event event;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Connection con = Database.getInstance().getConnection();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String docName = rs.getString("doctorname");
            int id = rs.getInt("ID");
            String custName = rs.getString("customername");
            String email = rs.getString("customeremail");
            String type = rs.getString("apptmttype");
            java.sql.Time startTime = rs.getTime("starttime");
            java.sql.Time endTime = rs.getTime("endtime");
            java.sql.Date dbSqlDate = rs.getDate("apptmtdate");

            String dateConverted = format.format(dbSqlDate);
            String startConverted = timeFormat.format(startTime);
            String endConverted = timeFormat.format(endTime);

            event = new Event(id, docName, dateConverted, startConverted, endConverted, custName, type, email);

            if (singleSelection) {
                if (docName.equals(name)) {
                    events.add(event);
                }
            } else {
                events.add(event);
            }
        }
        return events;
    }

    public static int retrieveUserIDbyName(String userName) throws SQLException {
        Connection con = Database.getInstance().getConnection();

        PreparedStatement ps = con.prepareStatement("SELECT FROM USERS WHERE FIRSTNAME = ?");
        ps.setString(1, userName);

        int userID;
        try (ResultSet rs = ps.executeQuery()) {
            userID = rs.getInt(1);
        }
        return userID;
    }

}