/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Prescription;

import Utils.Database;

import java.sql.*;

/**
 *
 * @author Bredan
 */
public class PrescriptionDAO {
    
    public static void insertPrescription(Prescription prescription){
        Connection con = Database.getInstance().getConnection();
        
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO PRESCRIPTION (PATIENTID, MEDICINE, QUANTITY, REPEATING, DATE_ISSUED, END_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, prescription.getPatientID());
            ps.setString(2, prescription.getMedicine());
            ps.setInt(3, prescription.getQuantity());
            ps.setBoolean(4, prescription.getRepeating());
            
            java.sql.Date issueDate = java.sql.Date.valueOf(prescription.getIssueDate());
            java.sql.Date endDate = java.sql.Date.valueOf(prescription.getEndDate());

            ps.setDate(5, issueDate);
            ps.setDate(6, endDate);
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

            Prescription prescription = new Prescription(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4), rs.getString(5), rs.getString(6));

            rs.close();
            
            return prescription;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new PrescriptionNotFoundException();
        }
    }    
    
}
