package Models.Event;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Log {
    public static void info(String description) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Event event = new Event(null, now, description);
        try {
            EventDao.insertEvent(event);
        } catch (SQLException ignored) {}
    }
}
