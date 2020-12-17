package Models.User;

import Utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class UserAccountDAO {
    public static boolean insertUserAccount(UserAccount user) throws DuplicateEmailPresentException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (FIRSTNAME, SURNAME, EMAIL, HASHED_PASSWORD, SALT, ROLE, ACTIVE) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setBytes(4, user.getPassword());
            ps.setBytes(5, user.getSalt());
            ps.setString(6, user.getRole());
            ps.setBoolean(7, user.isActive());
            ps.executeUpdate();

            System.out.println("User inserted");
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            throw new DuplicateEmailPresentException();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static UserAccount getUserAccount(int ID) throws UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new UserNotFoundException();
            }

            UserAccount user = new UserAccount(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getBytes("hashed_password"),
                    rs.getBytes("salt"),
                    rs.getString("role"),
                    rs.getBoolean("active")
            );

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static UserAccount getUserAccountByEmail(String email) throws UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new UserNotFoundException();
            }

            UserAccount user = new UserAccount(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getBytes("hashed_password"),
                    rs.getBytes("salt"),
                    rs.getString("role"),
                    rs.getBoolean("active")
            );

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static ArrayList<UserAccount> getAllUserAccounts() {
        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS");

            ResultSet rs = ps.executeQuery();

            ArrayList<UserAccount> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new UserAccount(
                        rs.getInt("ID"),
                        rs.getString("firstName"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getBytes("hashed_password"),
                        rs.getBytes("salt"),
                        rs.getString("role"),
                        rs.getBoolean("active")
                ));
            }

            rs.close();

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateUserAccount(UserAccount userAccount) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE USERS SET " +
                    "FIRSTNAME = ?, " +
                    "SURNAME = ?, " +
                    "EMAIL = ?, " +
                    "HASHED_PASSWORD = ?, " +
                    "SALT = ?, " +
                    "ROLE = ?, " +
                    "ACTIVE = ?" +
                    "WHERE ID = ?");
            ps.setString(1, userAccount.getFirstName());
            ps.setString(2, userAccount.getSurname());
            ps.setString(3, userAccount.getEmail());
            ps.setBytes(4, userAccount.getPassword());
            ps.setBytes(5, userAccount.getSalt());
            ps.setString(6, userAccount.getRole());
            ps.setBoolean(7, userAccount.isActive());
            ps.setInt(8, userAccount.getID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

