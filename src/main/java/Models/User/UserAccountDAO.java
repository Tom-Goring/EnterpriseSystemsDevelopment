package Models.User;

import static Models.User.UserDAO.getAllStaff;
import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAccountDAO {
    public static void insertUserAccount(UserAccount user) throws DuplicateEmailPresentException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (FIRSTNAME, SURNAME, EMAIL, HASHED_PASSWORD, SALT, ROLE) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setBytes(4, user.getPassword());
            ps.setBytes(5, user.getSalt());
            ps.setString(6, user.getRole());
            ps.executeUpdate();

            System.out.println("User inserted");

        } catch(SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            throw new DuplicateEmailPresentException();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                    rs.getString("role")
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
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBytes(5),
                    rs.getBytes(6),
                    rs.getString("role")
            );

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static ArrayList<UserAccount> getAllUserAccounts() throws SQLException {
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS");

        ResultSet rs = ps.executeQuery();

        ArrayList<UserAccount> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new UserAccount(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBytes(5),
                    rs.getBytes(6),
                    rs.getString(7)
            ));
        }

        rs.close();

        return users;
    }
         
}

