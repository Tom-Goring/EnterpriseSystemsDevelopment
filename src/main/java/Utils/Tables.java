package Utils;

import Models.Event.Log;
import Models.User.*;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
                    "role VARCHAR(16) not null, " +
                    "active boolean not null, " +
                    "DOB DATE not null, " +
                    "city varchar(30) not null, " +
                    "postcode varchar(20) not null, " +
                    "street varchar(40) not null, " +
                    "gender varchar(10), " +
                    "type varchar(20)" +
                    ")");
            ps.executeUpdate();
            System.out.println("Table created successfully!");

            Date generic_dob = Date.valueOf(LocalDate.now());
            Address generic_address = new Address("City", "AAA AAA", "Street");

            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash("admin");

            User admin = new User(null, "admin", "admin", "admin@admin.com", "admin", generic_dob, generic_address, Gender.Male, Type.Other);

            UserAccount adminAccount = new UserAccount(
                    admin,
                    saltAndHash.x,
                    saltAndHash.y,
                    true
            );
            UserAccountDAO.insertUserAccount(adminAccount);
            System.out.println("Admin user created successfully!");


            Tuple<byte[], byte[]> saltAndHash2 = createSaltAndHash("password");

            User doctorUser1 = new User(null, "doctor", "doctorson", "doctor1@doctor.com", "doctor", generic_dob, generic_address, Gender.Male, Type.Other);

            UserAccount doctor1 = new UserAccount(
                    doctorUser1,
                    saltAndHash2.x,
                    saltAndHash2.y,
                    true
            );

            UserAccountDAO.insertUserAccount(doctor1);
            System.out.println("Doctor1 user created successfully!");

            Tuple<byte[], byte[]> saltAndHash3 = createSaltAndHash("password");

            User doctorUser2 = new User(null, "femdoc", "doctorsdottir", "doctor2@doctor.com", "doctor", generic_dob, generic_address, Gender.Female, Type.Other);

            UserAccount doctor2 = new UserAccount(
                    doctorUser2,
                    saltAndHash3.x,
                    saltAndHash3.y,
                    true
            );
            UserAccountDAO.insertUserAccount(doctor2);
            System.out.println("Doctor2 user created successfully!");

            Tuple<byte[], byte[]> saltAndHash4 = createSaltAndHash("password");

            User patientUser = new User(null, "patient", "patientperson", "patient@patient.com", "patient", generic_dob, generic_address, Gender.Male, Type.PublicPatient);

            UserAccount patient = new UserAccount(
                    patientUser,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
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
                    "patientID int not null constraint patient_fk references users(ID) on delete cascade, " +
                    "staffID int not null constraint  staff_fk references users(ID) on delete cascade, " +
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

    public static void createApprovalsTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table approvals(" +
                    "ID int generated always as identity unique, " +
                    "accountID int not null constraint user_fk references users(ID) on delete cascade, " +
                    "actioned boolean" +
                    ")"
            );
            ps.executeUpdate();
            System.out.println("Approvals table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createScheduleTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table schedules("
                    + "ID int generated always as identity unique, "
                    + "staffID int not null unique constraint staff_to_schedule_fk references users(ID) on delete cascade, "
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
                    "patientID int not null constraint patientid_FK references USERS (ID) on delete cascade, " +
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

    public static void createSlotPricesTable() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table slotprices(" +
                    "ID int generated always as identity unique, " +
                    "slotInterval int not null, " +
                    "nurseCost int not null, " +
                    "doctorCost int not null, " +
                    "slotMaximumDuration int not null, " +
                    "slotMinimumDuration int not null)"
            );
            ps.executeUpdate();

            ps = con.prepareStatement("insert into SLOTPRICES (SLOTINTERVAL, NURSECOST, DOCTORCOST, SLOTMAXIMUMDURATION, SLOTMINIMUMDURATION) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, 10);
            ps.setBigDecimal(2, new BigDecimal(5));
            ps.setBigDecimal(3, new BigDecimal(10));
            ps.setInt(4, 30);
            ps.setInt(5, 10);
            ps.executeUpdate();

            System.out.println("Slot Prices table created successfully!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createPrescriptionsApprovalTable()
    {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("create table prescriptionsapprovals(" +
                    "ID int not null constraint psapproval references prescription(ID) on delete cascade, " +
                    "prescriptionID int not null constraint prescription_FK references prescription(ID) on delete cascade, " +
                    "actioned boolean, " +
                    "primary key(ID)"+
                    ")"
            );
            ps.executeUpdate();
            System.out.println("Prescriptions Approvals table created successfully!");
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
            PreparedStatement ps = con.prepareStatement("DROP TABLE approvals");
            ps.executeUpdate();
            System.out.println("Approvals table dropped");
        } catch (SQLException e) {}
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE prescriptionsapprovals");
            ps.executeUpdate();
            System.out.println("Prescriptions approval table dropped");
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
        try {
            PreparedStatement ps = con.prepareStatement("DROP TABLE slotprices");
            ps.executeUpdate();
            System.out.println("Slot prices table dropped");
        } catch (SQLException e) {}

        createUserTable();
        createEventTable();
        createApprovalsTable();
        createAppointmentsTable();
        createScheduleTable();
        createPrescriptionTable();
        createSlotPricesTable();
        createPrescriptionsApprovalTable();
        Log.info("Tables were recreated");
    }
    
}
