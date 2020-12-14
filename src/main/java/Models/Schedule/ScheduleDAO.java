/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Schedule;

import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserAccount;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;
import Utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxwell
 */
public class ScheduleDAO {

    public static void insertSchedule(Schedule schedule) throws SQLException {
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO schedules " +
                "(STAFFID, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)"
        );

        ps.setInt(1, schedule.getStaffID());
        ps.setBoolean(2, schedule.getScheduleBoolean(0));
        ps.setBoolean(3, schedule.getScheduleBoolean(1));
        ps.setBoolean(4, schedule.getScheduleBoolean(2));
        ps.setBoolean(5, schedule.getScheduleBoolean(3));
        ps.setBoolean(6, schedule.getScheduleBoolean(4));
        ps.setBoolean(7, schedule.getScheduleBoolean(5));
        ps.setBoolean(8, schedule.getScheduleBoolean(6));

        ps.executeUpdate();

        System.out.println("Schedule inserted");
    }

    public static void updateSchedule(Schedule schedule) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE schedules " +
                    "SET MONDAY = ?, " +
                    "TUESDAY = ?, " +
                    "WEDNESDAY = ?, " +
                    "THURSDAY = ?, " +
                    "FRIDAY = ?, " +
                    "SATURDAY = ?, " +
                    "SUNDAY = ? " +
                    "WHERE STAFFID = ?");

            ps.setBoolean(1, schedule.getScheduleBoolean(0));
            ps.setBoolean(2, schedule.getScheduleBoolean(1));
            ps.setBoolean(3, schedule.getScheduleBoolean(2));
            ps.setBoolean(4, schedule.getScheduleBoolean(3));
            ps.setBoolean(5, schedule.getScheduleBoolean(4));
            ps.setBoolean(6, schedule.getScheduleBoolean(5));
            ps.setBoolean(7, schedule.getScheduleBoolean(6));
            ps.setInt(8, schedule.getStaffID());

            ps.executeUpdate();

            System.out.println("Schedule inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean upsertSchedule(Schedule schedule) {
        try {
            insertSchedule(schedule);
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            updateSchedule(schedule);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Schedule retrieveSchedule(User user) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM schedules WHERE STAFFID = ?");
            ps.setInt(1, user.getID());

            ResultSet rs = ps.executeQuery();

            rs.next();

            return new Schedule(
                    user.getID(),
                    rs.getBoolean("MONDAY"),
                    rs.getBoolean("TUESDAY"),
                    rs.getBoolean("WEDNESDAY"),
                    rs.getBoolean("THURSDAY"),
                    rs.getBoolean("FRIDAY"),
                    rs.getBoolean("SATURDAY"),
                    rs.getBoolean("SUNDAY")
            );
        } catch (SQLException ex) {
//            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ArrayList<Schedule> getStaffSchedules() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM schedules");

            ArrayList<Schedule> schedules = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                 schedules.add(new Schedule(
                        rs.getInt("STAFFID"),
                        rs.getBoolean("MONDAY"),
                        rs.getBoolean("TUESDAY"),
                        rs.getBoolean("WEDNESDAY"),
                        rs.getBoolean("THURSDAY"),
                        rs.getBoolean("FRIDAY"),
                        rs.getBoolean("SATURDAY"),
                        rs.getBoolean("SUNDAY")
                ));
            }
            return schedules;
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
