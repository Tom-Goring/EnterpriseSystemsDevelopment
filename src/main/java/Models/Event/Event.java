package Models.Event;

import java.sql.Timestamp;

public class Event {
    private final Integer ID;
    private final Timestamp createdAt;
    private final String description;

    public Event(Integer ID, Timestamp createdAt, String description) {
        this.ID = ID;
        this.createdAt = createdAt;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }
}
