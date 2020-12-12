package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {

    public static void createUserTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table users(ID int generated always as identity, "
                    + "firstName varchar(30) not null, "
                    + "surname varchar(30) not null, "
                    + "email varchar(30) not null UNIQUE , "
                    + "hashed_password VARCHAR(128) FOR BIT DATA not null,"
                    + "salt VARCHAR(16) FOR BIT DATA not null"
                    + ")");
            ps.executeUpdate();
            System.out.println("Table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void creatAppointmentTaBle() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table appointments(ID int generated always as identity, "
                    + "customerName varchar(30) not null, "
                    + "doctorName varchar(30) not null, "
                    + "customerEmail varchar(30) not null, "
                    + "apptmtdate DATE not null, "
                    + "startTime TIME not null, "
                    + "endTime TIME not null, "
                    + "apptmttype varchar(30) not null, "
                    + "PRIMARY KEY (ID) "
                    + ")");

            ps.executeUpdate();
            System.out.println("Appointment Table created successfully!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
