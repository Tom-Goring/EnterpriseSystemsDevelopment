package Utils;

import Models.Event.Log;
import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserDao;

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
            PreparedStatement ps = con.prepareStatement("create table users(" +
                    "ID int generated always as identity unique, " +
                    "firstName varchar(30) not null, " +
                    "surname varchar(30) not null, " +
                    "email varchar(30) not null UNIQUE , " +
                    "hashed_password VARCHAR(128) FOR BIT DATA not null," +
                    "salt VARCHAR(16) FOR BIT DATA not null, " +
                    "role VARCHAR(16) not null" +
                    ")");
            ps.executeUpdate();
            System.out.println("Table created successfully!");
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash("password");
            User admin = new User(null,
                    "admin",
                    "",
                    "admin@admin.com",
                    saltAndHash.x,
                    saltAndHash.y,
                    "admin"
            );
            UserDao.insertUser(admin);
            System.out.println("Admin user created successfully!");
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException | DuplicateEmailPresentException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createEventTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table events(" +
                    "ID int generated always as identity unique, " +
                    "CreatedAt TIMESTAMP, " +
                    "Description long varchar" +
                    ")");
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
                    "endTime time not null" +
                    ")");
            ps.executeUpdate();
            System.out.println("Appointments table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
	    public static void createPrescriptionTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table prescription(" + 
                    "prescription_no int IDENTITY(1,1) primary key, " +
                    "customerID int FOREIGN KEY REFERENCES users(ID), " +
                    "customerFName varchar(30) FOREIGN KEY REFERENCES users(firstName), " +
                    "customerSName varchar)(30) FOREIGN KEY REFERENCES users(surname), " +
                    "medicine varchar(30) not null, " +
                    "quantity int(10) not null, " +
                    "repeating boolean not null, " +
                    "date_issued date not null, " +
                    "end_date date" +
                    ")");
            ps.executeUpdate();
            System.out.println("Table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
	
HEAD


    public static void recreateTables() {
        System.out.println("Recreating database table structure...");
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE events");
            ps.executeUpdate();
            System.out.println("Events table dropped");
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE appointments");
            ps.executeUpdate();
            System.out.println("Appointments table dropped");
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE users");
            ps.executeUpdate();
            System.out.println("Users table dropped");
        } catch (SQLException e) {e.printStackTrace();}

        createUserTable();
        createEventTable();
        createAppointmentsTable();
        Log.info("Tables were recreated");
    }
    
    
 f721239... Update Tables.java
}
