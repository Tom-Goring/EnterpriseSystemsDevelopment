/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Prescription;

import Models.User.UserDAO;
import Models.User.UserNotFoundException;
import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bredan
 */
public class PrescriptionDAO {

    public static void insertPrescription(Prescription prescription) {
        Connection con = Database.getInstance().getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO PRESCRIPTION (PATIENTID, MEDICINE, QUANTITY, REPEATING, DATE_ISSUED, END_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, prescription.getPatient().getID());
            ps.setString(2, prescription.getMedicine());
            ps.setInt(3, prescription.getQuantity());
            ps.setBoolean(4, prescription.getRepeating());
            ps.setDate(5, prescription.getIssueDate());
            ps.setDate(6, prescription.getEndDate());
            ps.executeUpdate();

            System.out.println("Prescription inserted");
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Prescription getPrescription(int ID) throws PrescriptionNotFoundException, UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRESCRIPTION WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == ID) {
                    Prescription prescription = new Prescription(
                            rs.getInt(1),
                            UserDAO.getUser(rs.getInt("patientID")),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getBoolean(5),
                            rs.getDate(6),
                            rs.getDate(7)
                    );

                    rs.close();
                    return prescription;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new PrescriptionNotFoundException();
        }
        return null;
    }

    public static ArrayList<String> retrievePatients() {
        ArrayList<String> patients = new ArrayList<>();
        Connection con = Database.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String role = rs.getString("role");
                if ("patient".equals("role")) {
                    patients.add(rs.getString("SURNAME") + ", " + rs.getString("FIRSTNAME"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, e);
        }
        return patients;
    }

    public static void updatePrescription(Prescription prescription) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE PRESCRIPTION SET REPEATING = ? WHERE ID = ?");
            ps.setBoolean(1, true);
            ps.setInt(2, prescription.getID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Prescription> getAllPrescriptionsForUser(Integer userID) throws SQLException, UserNotFoundException {
        Connection con = Database.getInstance().getConnection();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRESCRIPTION WHERE PATIENTID = ?");
        ps.setInt(1, userID);

        ResultSet rs = ps.executeQuery();

        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
        while (rs.next()) {
            prescriptions.add(new Prescription(
                    rs.getInt(1),
                    UserDAO.getUser(rs.getInt("patientID")),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getDate(6),
                    rs.getDate(7)
            ));
        }

        rs.close();

        return prescriptions;
    }
}
