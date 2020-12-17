package Models.Approval;

import Models.User.*;
import Utils.Database;

import java.sql.*;
import java.util.ArrayList;

public class ApprovalDAO {
    public static void insertApproval(Approval approval) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO approvals (ACCOUNTID, ACTIONED) VALUES (?, ?)");
            ps.setInt(1, approval.getAccount().getID());
            ps.setBoolean(2, approval.isActioned());
            ps.executeUpdate();

            System.out.println("Approval inserted");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateApproval(Approval approval) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE approvals SET ACTIONED = ? WHERE ID = ?");
            ps.setBoolean(1, approval.actioned);
            ps.setInt(2, approval.getID());
            ps.executeUpdate();

            System.out.println("Approval updated");

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Approval getApproval(int ID) {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM approvals WHERE ID = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Approval(
                    rs.getInt("ID"),
                    UserAccountDAO.getUserAccount(rs.getInt("accountID")),
                    rs.getBoolean("actioned")
            );
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Approval> getAllPendingApprovals() {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM approvals WHERE ACTIONED = false");
            ResultSet rs = ps.executeQuery();

            ArrayList<Approval> approvals = new ArrayList<>();
            while (rs.next()) {
                try {
                    approvals.add(new Approval(
                            rs.getInt("ID"),
                            UserAccountDAO.getUserAccount(rs.getInt("accountID")),
                            rs.getBoolean("actioned")
                    ));
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            return approvals;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
