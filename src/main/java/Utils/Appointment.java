package Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

/**
 *
 * @author maxwell
 */
public final class Appointment {

    private final String username = "appointment";
    private final String password = "appointment";
    private final String dayStart = "9:00";
    private final String dayEnd = "18:00";
    ArrayList<Event> events;
    Event event;
    HashMap<String, Integer> doctors;
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public Appointment() {
        retrieveDoctorsfromDB();
    }

    public void retrieveDoctorsfromDB() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String sql = "SELECT * FROM DOCTOR";
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Appointments", username, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            doctors = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                doctors.put(name, id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getDoctors() {
        Set<String> keySet = doctors.keySet();
        ArrayList<String> listOfdoctors = new ArrayList<>(keySet);
        return listOfdoctors;
    }

    public ArrayList<Event> retrieveEventsbyDoctorID(int ID) {
        try {
            events = new ArrayList<>();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String sql = "SELECT * FROM APPOINTMENTS";
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Appointments", username, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int docID = rs.getInt("DOCTORID");
                if (docID == ID) {
                    String docName = rs.getString("DOCTORNAME");
                    String custName = rs.getString("CUSTOMERNAME");
                    String type = rs.getString("TYPE");
                    java.sql.Time startTime = rs.getTime("APPOINTMENTTIME");
                    java.sql.Time endTime = rs.getTime("ENDTIME");
                    java.sql.Date dbSqlDate = rs.getDate("APPOINTMENTDATE");

                    String dateConverted = format.format(dbSqlDate);
                    String startConverted = timeFormat.format(startTime);
                    String endConverted = timeFormat.format(endTime);

                    event = new Event(docName, dateConverted, startConverted, endConverted, custName, type);
                    events.add(event);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return events;
    }

    public void addAppointmentToDatabase(Event event) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String sql = "INSERT INTO Appointments(APPOINTMENTDATE,APPOINTMENTID,APPOINTMENTTIME,DOCTORID,ENDTIME,CUSTOMERNAME,TYPE,DOCTORNAME) VALUES(?,?,?,?,?,?,?,?)";
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Appointments", username, password);
            PreparedStatement prep = connection.prepareStatement(sql);

            LocalTime s = LocalTime.parse(event.getStartTime());
            LocalTime e = LocalTime.parse(event.getEndTime());

            Time start = Time.valueOf(s);
            Time end = Time.valueOf(e);

            java.sql.Date date = java.sql.Date.valueOf(event.getDate());

            int id = getAppointmentID();
            int docID = doctors.get(event.getDoctorName());
            String docName = event.getDoctorName();
            String custName = event.getCustomerName();
            String type = event.getType();

            prep.setDate(1, date);
            prep.setInt(2, id);
            prep.setTime(3, start);
            prep.setInt(4, docID);
            prep.setTime(5, end);
            prep.setString(6, custName);
            prep.setString(7, type);
            prep.setString(8, docName);
            prep.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public int getAppointmentID() {
        int count = 0;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String sql = "SELECT count(*) FROM APPOINTMENTS";
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Appointments", username, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return count + 1;
    }

    public ArrayList<Event> sortAppointmentsByDate(String appointmentDate, ArrayList<Event> event) {
        ArrayList<Event> e = new ArrayList<>();

        String[] temp = appointmentDate.split("-");

        event.stream().map(new Function<Event, Event>() {
            @Override
            public Event apply(Event event1) {
                System.out.println(event1.getDate());
                return event1;
            }
        }).filter((event1) -> (event1.getDate().equals(appointmentDate))).forEachOrdered((event1) -> {
            e.add(event1);
        });
        return e;
    }

    public int getDoctorIDbyName(String name) {
        int docID = doctors.get(name);
        return docID;
    }

    public ArrayList<String> getGeneratedSlots(int apptmLength, int doctorID, String date) {
        ArrayList<Event> currEvents = new ArrayList<>();
        ArrayList<String> intervals = new ArrayList<>();
        String[] hours = new String[2];
        boolean addSlot = false;

        currEvents = retrieveEventsbyDoctorID(doctorID);
        currEvents = sortAppointmentsByDate(date, currEvents);

        try {
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(timeFormat.parse(dayStart));
            cal2.setTime(timeFormat.parse(dayEnd));
            Time u = new java.sql.Time(cal.getTimeInMillis());
            hours[0] = timeFormat.format(u);

            while (cal.getTime().before(cal2.getTime())) {
                cal.add(Calendar.MINUTE, apptmLength);
                Time startTime = new java.sql.Time(cal.getTimeInMillis());
                String x = timeFormat.format(startTime);
                hours[1] = x;
                addSlot = compareSelectedTime(hours, currEvents);
                if (addSlot == true) {
                    String hoursRange = hours[0] + '-' + hours[1];
                    intervals.add(hoursRange);
                }
                String temp = hours[1];
                hours = new String[2];
                hours[0] = temp;

            }
        } catch (ParseException e) {
            System.out.println(e);
        }
        return intervals;
    }

    public boolean compareSelectedTime(String[] time, ArrayList<Event> currEvents) {

        int slotStart = Integer.parseInt(time[0].replace(":", ""));
        int slotEnd = Integer.parseInt(time[1].replace(":", ""));
        for (Event currEvent : currEvents) {
            int appointmentstart = Integer.parseInt(currEvent.getStartTime().replace(":", ""));
            int appointmentend = Integer.parseInt(currEvent.getEndTime().replace(":", ""));

            if (slotStart >= appointmentstart && slotEnd <= appointmentend) {
                return false;
            } else if (slotStart >= appointmentstart && slotStart <= appointmentend) {
                return false;
            } else if ((slotStart < appointmentstart) && slotEnd >= appointmentstart && slotEnd <= appointmentend) {
                return false;
            }
        }
        return true;
    }
    public String[] formatSelectedSlot(String slot)
    {
        String [] time = new String[2];
        time = slot.split("-");
        
        return time;
        
    }
}
