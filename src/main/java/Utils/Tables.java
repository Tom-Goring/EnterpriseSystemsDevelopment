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
            
            // DOBs for test users
            Date generic_dob1 = Date.valueOf("1991-12-16");
            Date generic_dob2 = Date.valueOf("1995-04-07");
            Date generic_dob3 = Date.valueOf("2001-06-05");
            Date generic_dob4 = Date.valueOf("2008-05-26");
            Date generic_dob5 = Date.valueOf("2019-01-22");
            
            // addresses for test users
            Address generic_address1 = new Address("Birkenhead", "CH42 5PX", "52 Grasville Road");
            Address generic_address2 = new Address("Newport", "TF10 7FU", "14 Keller Drive");
            Address generic_address3 = new Address("Collyweston", "PE9 3PY", "11 Woodfield");
            Address generic_address4 = new Address("Mortehoe", "EX34 7ER", "7 Pebbles Court");
            Address generic_address5 = new Address("Grimsby", "DN34 5QP", "10 Africa Close");

            
            // admin test user
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

            // doctor 1 test user
            Tuple<byte[], byte[]> saltAndHash2 = createSaltAndHash("password");

            User doctorUser1 = new User(null, "doctor", "First", "doctor1@doctor.com", "doctor", generic_dob, generic_address, Gender.Male, Type.Other);

            UserAccount doctor1 = new UserAccount(
                    doctorUser1,
                    saltAndHash2.x,
                    saltAndHash2.y,
                    true
            );

            UserAccountDAO.insertUserAccount(doctor1);
            System.out.println("Doctor1 user created successfully!");
            
            // doctor 2 test user
            Tuple<byte[], byte[]> saltAndHash3 = createSaltAndHash("password");

            User doctorUser2 = new User(null, "doctor", "Second", "doctor2@doctor.com", "doctor", generic_dob, generic_address, Gender.Female, Type.Other);

            UserAccount doctor2 = new UserAccount(
                    doctorUser2,
                    saltAndHash3.x,
                    saltAndHash3.y,
                    true
            );
            UserAccountDAO.insertUserAccount(doctor2);
            System.out.println("Doctor2 user created successfully!");

            // patient 1 test user Rob Smith
            Tuple<byte[], byte[]> saltAndHash4 = createSaltAndHash("password");
            User patientUser1 = new User(null, "Rob", "Smith", "RSmith@patient.com", "patient", generic_dob1, generic_address1, Gender.Male, Type.PublicPatient);
            UserAccount patient1 = new UserAccount(
                    patientUser1,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
            );
            UserAccountDAO.insertUserAccount(patient1);
            System.out.println("patient test user 1 created successfully!");
            
            // patient 2 test user Liz Brown
            User patientUser2 = new User(null, "Liz", "Brown", "LBrown@patient.com", "patient", generic_dob2, generic_address2, Gender.Female, Type.PublicPatient);
            UserAccount patient2 = new UserAccount(
                    patientUser2,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
            );
            UserAccountDAO.insertUserAccount(patient2);
            System.out.println("patient test user 2 created successfully!");
           
            // patient 3 test user Brad Hesistant
            User patientUser3 = new User(null, "Brad", "Hesitant", "BHesitant@patient.com", "patient", generic_dob3, generic_address3, Gender.Male, Type.PublicPatient);
            UserAccount patient3 = new UserAccount(
                    patientUser3,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
            );
            UserAccountDAO.insertUserAccount(patient3);
            System.out.println("patient test user 3 created successfully!");
            
            // patient 4 test user Maddie Clement
            User patientUser4 = new User(null, "Maddie", "Clement", "MClement@patient.com", "patient", generic_dob4, generic_address4, Gender.Female, Type.PrivatePatient);
            UserAccount patient4 = new UserAccount(
                    patientUser4,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
            );
            UserAccountDAO.insertUserAccount(patient4);
            System.out.println("patient test user 4 created successfully!");

            // patient 5 test user Maria Ryley
            User patientUser5 = new User(null, "Maria", "Ryley", "MRyley@patient.com", "patient", generic_dob5, generic_address5, Gender.Female, Type.PrivatePatient);
            UserAccount patient5 = new UserAccount(
                    patientUser5,
                    saltAndHash4.x,
                    saltAndHash4.y,
                    true
            );
            UserAccountDAO.insertUserAccount(patient5);
            System.out.println("patient test user 5 created successfully!");
            
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
