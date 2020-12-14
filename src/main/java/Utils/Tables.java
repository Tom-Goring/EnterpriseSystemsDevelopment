package Utils;

import Models.Event.Log;
import Models.User.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Utils.Passwords.createSaltAndHash;

public class Tables {
    public static void createUserTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table users("
                    + "ID int generated always as identity unique, "
                    + "firstName varchar(30) not null, "
                    + "surname varchar(30) not null, "
                    + "email varchar(30) not null UNIQUE , "
                    + "hashed_password VARCHAR(128) FOR BIT DATA not null,"
                    + "salt VARCHAR(16) FOR BIT DATA not null, "
                    + "role VARCHAR(16) not null"
                    + ")");
            ps.executeUpdate();
            System.out.println("Table created successfully!");
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash("admin");
            UserAccount admin = new UserAccount(null,
                    "admin",
                    "",
                    "admin",
                    saltAndHash.x,
                    saltAndHash.y,
                    "admin"
            );
            UserAccountDAO.insertUserAccount(admin);
            System.out.println("Admin user created successfully!");

            Tuple<byte[], byte[]> saltAndHash2 = createSaltAndHash("password");
            UserAccount doctor1 = new UserAccount(null,
                    "doctor",
                    "",
                    "doctor1",
                    saltAndHash2.x,
                    saltAndHash2.y,
                    "doctor"
            );
            UserAccountDAO.insertUserAccount(doctor1);
            System.out.println("Doctor1 user created successfully!");

            Tuple<byte[], byte[]> saltAndHash3 = createSaltAndHash("password");
            UserAccount doctor2 = new UserAccount(null,
                    "doctor2",
                    "",
                    "doctor2",
                    saltAndHash3.x,
                    saltAndHash3.y,
                    "doctor"
            );
            UserAccountDAO.insertUserAccount(doctor2);
            System.out.println("Doctor2 user created successfully!");

            Tuple<byte[], byte[]> saltAndHash4 = createSaltAndHash("password");
            UserAccount patient = new UserAccount(null,
                    "patient",
                    "",
                    "patient",
                    saltAndHash4.x,
                    saltAndHash4.y,
                    "patient"
            );
            UserAccountDAO.insertUserAccount(patient);
            System.out.println("patient user created successfully!");


        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException | DuplicateEmailPresentException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createEventTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table events("
                    + "ID int generated always as identity unique, "
                    + "CreatedAt TIMESTAMP, "
                    + "Description long varchar"
                    + ")");
            ps.executeUpdate();
            System.out.println("Events table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createAppointmentsTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table appointments(" +
                    "ID int generated always as identity unique, " +
                    "patientID int not null constraint patient_fk references users(ID), " +
                    "staffID int not null constraint  staff_fk references users(ID), " +
                    "date date not null, " +
                    "startTime time not null, " +
                    "endTime time not null, " +
                    "type varchar(20) not null" +
                    ")");
            ps.executeUpdate();
            System.out.println("Appointments table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createScheduleTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table schedules("
                    + "ID int generated always as identity unique, "
                    + "staffID int not null unique constraint staff_to_schedule_fk references users(ID), "
                    + "Monday boolean default false, "
                    + "Tuesday boolean default false, "
                    + "Wednesday boolean default false, "
                    + "Thursday boolean default false, "
                    + "Friday boolean default false, "
                    + "Saturday boolean default false, "
                    + "Sunday boolean default false"
                    + ")");
            ps.executeUpdate();
            System.out.println("Schedules table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void createPrescriptionTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table prescription(" +
                    "ID int generated always as identity unique, " +
                    "patientID int not null constraint patientid_FK references USERS (ID), " +
                    "medicine varchar(30) not null, " +
                    "quantity int not null, " +
                    "repeating boolean not null, " +
                    "date_issued date not null, " +
                    "end_date date" +
                    ")");
            ps.executeUpdate();
            System.out.println("Prescription table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void recreateTables() {
        System.out.println("Recreating database table structure...");
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE schedules");
            ps.executeUpdate();
            System.out.println("Schedule table dropped");
        } catch (SQLException e) {}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE events");
            ps.executeUpdate();
            System.out.println("Events table dropped");
        } catch (SQLException e) {}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE appointments");
            ps.executeUpdate();
            System.out.println("Appointments table dropped");
        } catch (SQLException e) {}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE prescription");
            ps.executeUpdate();
            System.out.println("Prescription table dropped");
        } catch (SQLException e) {}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE users");
            ps.executeUpdate();
            System.out.println("Users table dropped");
        } catch (SQLException e) {}

        createUserTable();
        createEventTable();
        createAppointmentsTable();
        createScheduleTable();
        createPrescriptionTable();
        Log.info("Tables were recreated");
    }
}
