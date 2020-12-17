/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Prescription;

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
    
    public static void insertPrescription(Prescription prescription){
        Connection con = Database.getInstance().getConnection();
        
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO PRESCRIPTION (PATIENTID, MEDICINE, QUANTITY, REPEATING, DATE_ISSUED, END_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, prescription.getPatient());
            ps.setString(2, prescription.getMedicine());
            ps.setInt(3, prescription.getQuantity());
            ps.setBoolean(4, prescription.getRepeating());
            ps.setDate(5, prescription.getIssueDate());
            ps.setDate(6, prescription.getEndDate());
            ps.executeUpdate();

            System.out.println("Prescription inserted");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static Prescription getPrescription(int ID) throws PrescriptionNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRESCRIPTION WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            Prescription prescription = new Prescription(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getDate(6),
                    rs.getDate(7)
            );

            rs.close();
            
            return prescription;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new PrescriptionNotFoundException();
        }
    }

    public static ArrayList<String> retrievePatients() {
        ArrayList<String> patients = new ArrayList<>();
        Connection con = Database.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String role = rs.getString("role");
                if ("patient".equals("role")){
                    patients.add(rs.getString("SURNAME")+", "+rs.getString("FIRSTNAME"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(Prescription.class.getName()).log(Level.SEVERE, null, e);
        }
        return patients;
    }

    public static ArrayList<Prescription> getAllPrescriptionsForUser(Integer userID) throws SQLException{
        Connection con = Database.getInstance().getConnection();
        
        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRESCRIPTION WHERE PATIENTID = ?");
        ps.setInt(1, userID);
        
        ResultSet rs = ps.executeQuery();

        ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
        while (rs.next()) {
            prescriptions.add(new Prescription(
                    rs.getInt(1),
                    rs.getInt(2),
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
