package Models.User;

import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAccountDAO {

    public static boolean insertUserAccount(UserAccount user) throws DuplicateEmailPresentException {
        Connection con = Database.getInstance().getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (firstname, surname, email, hashed_password, salt, role, active, dob, city, postcode, street, gender, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setBytes(4, user.getPassword());
            ps.setBytes(5, user.getSalt());
            ps.setString(6, user.getRole());
            ps.setBoolean(7, user.isActive());
            ps.setDate(8, user.getDOB());
            ps.setString(9, user.getAddress().getCity());
            ps.setString(10, user.getAddress().getPostcode());
            ps.setString(11, user.getAddress().getStreet());
            ps.setString(12, user.getGender().toString());
            ps.setString(13, user.getType().toString());

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

            User user = new User(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getDate("DOB"),
                    new Address(
                            rs.getString("city"),
                            rs.getString("postcode"),
                            rs.getString("street")
                    ),
                    Gender.fromString(rs.getString("gender")),
                    Type.fromString(rs.getString("type"))
            );

            UserAccount userAccount = new UserAccount(
                    user,
                    rs.getBytes("hashed_password"),
                    rs.getBytes("salt"),
                    rs.getBoolean("active")
            );

            rs.close();

            return userAccount;

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

            User user = new User(
                    rs.getInt("ID"),
                    rs.getString("firstName"),
                    rs.getString("surname"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getDate("DOB"),
                    new Address(
                            rs.getString("city"),
                            rs.getString("postcode"),
                            rs.getString("street")
                    ),
                    Gender.fromString(rs.getString("gender")),
                    Type.fromString(rs.getString("type"))
            );

            UserAccount userAccount = new UserAccount(
                    user,
                    rs.getBytes("hashed_password"),
                    rs.getBytes("salt"),
                    rs.getBoolean("active")
            );

            rs.close();

            return userAccount;

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
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("firstName"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getDate("DOB"),
                        new Address(
                                rs.getString("city"),
                                rs.getString("postcode"),
                                rs.getString("street")
                        ),
                        Gender.fromString(rs.getString("gender")),
                        Type.fromString(rs.getString("type"))
                );

                UserAccount userAccount = new UserAccount(
                        user,
                        rs.getBytes("hashed_password"),
                        rs.getBytes("salt"),
                        rs.getBoolean("active")
                );
                users.add(userAccount);
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
            PreparedStatement ps = con.prepareStatement("UPDATE USERS SET "
                    + "FIRSTNAME = ?, "
                    + "SURNAME = ?, "
                    + "EMAIL = ?, "
                    + "HASHED_PASSWORD = ?, "
                    + "SALT = ?, "
                    + "ROLE = ?, "
                    + "ACTIVE = ? "
                    + "WHERE ID = ?");
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

    public static void deleteUserAccount(int ID) {
        Connection con = Database.getInstance().getConnection();
        try {

            PreparedStatement ps = con.prepareStatement("DELETE FROM USERS WHERE ID = ?");
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateUserAccountDetails(int ID, String email, String role, String firstname, String surname) throws SQLException, DuplicateEmailPresentException {

        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement ps =
                    con.prepareStatement("UPDATE USERS SET " +
                            "FIRSTNAME = ?, " +
                            "SURNAME = ?, " +
                            "EMAIL = ?, " +
                            "ROLE = ? " +
                            "WHERE ID = ?"
                    );
            ps.setString(1, firstname);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, role);
            ps.setInt(5, ID);
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            throw new DuplicateEmailPresentException();

        }

    }
}
