/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PrescriptionsApproval;

import Models.Prescription.PrescriptionDAO;
import Models.Prescription.PrescriptionNotFoundException;
import Models.User.UserNotFoundException;
import Utils.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maxwell
 */
public class PrescriptionsApprovalDAO {

    public static void insertApproval(PrescriptionsApproval prescription) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO prescriptionsapprovals (ID, prescriptionID, ACTIONED) VALUES (?, ?, ?)");
            ps.setInt(1, prescription.getPrescription().getID());
            ps.setInt(2, prescription.getPrescription().getID());
            ps.setBoolean(3, prescription.isActioned());
            ps.executeUpdate();
            System.out.println("Prescription Approval inserted");
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                try {
                    PreparedStatement ps = con.prepareStatement("UPDATE prescriptionsapprovals SET ACTIONED =? WHERE ID=?");
                    ps.setBoolean(1, prescription.isActioned());
                    ps.setInt(2, prescription.getPrescription().getID());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(PrescriptionsApprovalDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static void updatePrescriptionApproval(PrescriptionsApproval prescription) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE prescriptionsapprovals SET ACTIONED = ? WHERE ID = ?");
            ps.setBoolean(1, prescription.actioned);
            ps.setInt(2, prescription.getID());
            ps.executeUpdate();

            System.out.println("Prescription Approval updated");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static PrescriptionsApproval getPrescriptionApproval(int ID) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prescriptionsapprovals WHERE ID = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new PrescriptionsApproval(
                    rs.getInt("ID"),
                    PrescriptionDAO.getPrescription(rs.getInt("prescriptionID")),
                    rs.getBoolean("actioned")
            );
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (PrescriptionNotFoundException ex) {
            Logger.getLogger(PrescriptionsApprovalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<PrescriptionsApproval> getAllPendingPrescriptionsApprovals() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prescriptionsapprovals WHERE ACTIONED = false");
            ResultSet rs = ps.executeQuery();

            ArrayList<PrescriptionsApproval> approvals = new ArrayList<>();
            while (rs.next()) {
                    approvals.add(new PrescriptionsApproval(
                            rs.getInt("ID"),
                            PrescriptionDAO.getPrescription(rs.getInt("prescriptionID")),
                            rs.getBoolean("actioned")
                    ));
            }
            rs.close();
            return approvals;
        } catch (SQLException | PrescriptionNotFoundException | UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
