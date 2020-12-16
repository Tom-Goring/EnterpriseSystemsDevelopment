package Models.User;

import Utils.Database;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {

    public static boolean insertUser(User user) throws DuplicateEmailPresentException {
        Connection con = Database.getInstance().getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO USERS (FIRSTNAME, SURNAME, EMAIL, HASHED_PASSWORD, SALT, ROLE) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setBytes(4, user.getPassword());
            ps.setBytes(5, user.getSalt());
            ps.setString(6, user.getRole());
            ps.executeUpdate();

            System.out.println("User inserted");
            return true;

        } catch(SQLIntegrityConstraintViolationException e) {
            throw new DuplicateEmailPresentException();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static User getUser(int ID) throws UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new UserNotFoundException();
            }

            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBytes(5), rs.getBytes(6), rs.getString("role"));

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static User getUserByEmail(String email) throws UserNotFoundException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new UserNotFoundException();
            }

            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBytes(5), rs.getBytes(6), rs.getString("role"));

            rs.close();

            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS");

        ResultSet rs = ps.executeQuery();

        ArrayList<User> users = new ArrayList<User>();
        while (rs.next()) {
            users.add(new User(
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
