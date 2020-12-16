package Models.Event;

import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    public static List<Event> getAllEvents() throws SQLException {
        Connection con = Database.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM EVENTS ORDER BY CreatedAt DESC");
        ResultSet rs = ps.executeQuery();

        ArrayList<Event> events = new ArrayList<Event>();
        while (rs.next()) {
            events.add(
                    new Event(
                            rs.getInt("ID"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getString("description")
                    )
            );
        }
        return events;
    }

    public static void insertEvent(Event event) throws SQLException {
       Connection con = Database.getInstance().getConnection();
       PreparedStatement ps = con.prepareStatement("INSERT INTO EVENTS (CreatedAt, Description) VALUES (?, ?)");
       ps.setTimestamp(1, event.getCreatedAt());
       ps.setString(2, event.getDescription());
       ps.executeUpdate();

       System.out.println("Event inserted");
    }
}
