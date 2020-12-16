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
            
            // staff test user
//            Tuple<byte[], byte[]> saltAndHash1 = createSaltAndHash("staff");
//            User staff = new User(1,
//                    "staff",
//                    "",
//                    "staff@care.com",
//                    saltAndHash1.x,
//                    saltAndHash1.y,
//                    "staff"
//                    );
//            UserDao.insertUser(staff);
//            System.out.println("Staff user created successfully!");
            
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
            PreparedStatement ps = con.prepareStatement("DROP TABLE prescription");
            ps.executeUpdate();
            System.out.println("Prescription table dropped");
        } catch (SQLException e) {e.printStackTrace();}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE users");
            ps.executeUpdate();
            System.out.println("Users table dropped");
        } catch (SQLException e) {e.printStackTrace();}

        

        createUserTable();
        createEventTable();
        createAppointmentsTable();
        createPrescriptionTable();
        Log.info("Tables were recreated");
    }
    
}
