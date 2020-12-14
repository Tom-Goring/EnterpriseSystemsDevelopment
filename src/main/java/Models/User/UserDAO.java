package Models.User;

import Models.Appointment.Appointment;
import Models.Schedule.Schedule;
import Models.Schedule.ScheduleDAO;
import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    public static User getUser(int ID) throws UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new UserNotFoundException();
            }

            User user = new User(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("role")
            );

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS");

        ResultSet rs = ps.executeQuery();

        ArrayList<User> users = new ArrayList<User>();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("role")
            ));
        }

        rs.close();

        return users;
    }

    public static ArrayList<User> getAllStaff() throws SQLException {
        ArrayList<User> staff = new ArrayList<>();
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT ID, FIRSTNAME, SURNAME, EMAIL, ROLE FROM users WHERE role = ? OR role = ?");
            ps.setString(1, "doctor");
            ps.setString(2, "nurse");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                staff.add(new User(
                        rs.getInt("ID"),
                        rs.getString("firstName"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("role")
                ));
            }

            for (User user : staff) {
                Schedule schedule = ScheduleDAO.retrieveSchedule(user);
                if (schedule != null) {
                    user.setSchedule(schedule);
                } else {
                    user.setSchedule(new Schedule());
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        }
        return staff;
    }
    
    public static ArrayList<User> getAvailableStaff(String day)
    {       
        ArrayList<User> staff = new ArrayList<>();
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * from schedules");
//            PreparedStatement ps = con.prepareStatement("SELECT STAFFID " +day+" from schedules");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                boolean availableonDay = rs.getBoolean(day);
                int staffid = rs.getInt("staffID");
                if(availableonDay==true)
                {
                    staff.add(getUser(staffid));
                    //temp.add(Integer.SIZE)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        } catch (UserNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
        
    }
}
    
   
