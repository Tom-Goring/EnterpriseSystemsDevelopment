package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Database dbInstance;
    private static Connection con;

    private Database() {
        // private constructor //
    }

    public static Database getInstance() {
        if (dbInstance == null) {
            dbInstance = new Database();
        }
        return dbInstance;
    }

    public Connection getConnection() {
        Config cfg = new Config();
        if (con == null) {
            try {
                String host = cfg.getProperty("jdbc_database_url");
                con = DriverManager.getConnection(host);
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return con;
    }
}