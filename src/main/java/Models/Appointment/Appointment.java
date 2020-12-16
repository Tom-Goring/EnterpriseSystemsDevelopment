/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Appointment;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author maxwell
 */
public class Appointment {

    private ArrayList<Event> sortAppointmentsByDate(String appointmentDate, ArrayList<Event> event) {
        ArrayList<Event> e = new ArrayList<>();

        event.stream().peek((Event event1) -> System.out.println(event1.getDate())).filter((event1) -> (event1.getDate().equals(appointmentDate))).forEachOrdered(e::add);
        return e;
    }

    private ArrayList<String> getGeneratedSlots(int apptmLength, String doctorName, String date) {
        ArrayList<Event> currEvents;
        ArrayList<String> intervals = new ArrayList<>();
        try {
            String[] hours = new String[2];
            boolean addSlot;
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            currEvents = AppointmentDAO.retrieveDoctorsAppointment(doctorName, true);
            currEvents = sortAppointmentsByDate(date, currEvents);

            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(timeFormat.parse("9:00"));
            cal2.setTime(timeFormat.parse("18:00"));
            Time u = new java.sql.Time(cal.getTimeInMillis());
            hours[0] = timeFormat.format(u);

            while (cal.getTime().before(cal2.getTime())) {
                cal.add(Calendar.MINUTE, apptmLength);
                Time startTime = new java.sql.Time(cal.getTimeInMillis());
                String x = timeFormat.format(startTime);
                hours[1] = x;
                addSlot = isAvailableSlot(hours, currEvents);
                if (addSlot) {
                    String hoursRange = hours[0] + '-' + hours[1];
                    intervals.add(hoursRange);
                }
                String temp = hours[1];
                hours[0] = temp;
            }
        } catch (ParseException | SQLException e) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, e);
        }
        return intervals;
    }

    private boolean isAvailableSlot(String[] time, ArrayList<Event> currEvents) {

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

    private String[] formatSelectedSlot(String slot) {
        String[] time;
        time = slot.split("-");
        return time;
    }

    private RequestDispatcher addAppointment(HttpServletRequest request) {
        System.out.println("add");
        ArrayList<String> doctorsList;
        doctorsList = AppointmentDAO.retrieveDoctors();
        request.setAttribute("doctorsList", doctorsList);
        request.setAttribute("task", "add");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher deleteAppointment(HttpServletRequest request) {
        ArrayList<Event> allAppointments;
        RequestDispatcher dispatcher = null;
        try {
            allAppointments = AppointmentDAO.retrieveDoctorsAppointment(" ", false);
            request.setAttribute("appointments", allAppointments);
            request.setAttribute("task", "delete");
            dispatcher = request.getRequestDispatcher("AppointmentPages.jsp");

        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dispatcher;
    }

    private RequestDispatcher viewAppointments(HttpServletRequest request) {
        System.out.println("view");
        ArrayList<String> doctorsList;
        doctorsList = AppointmentDAO.retrieveDoctors();
        request.setAttribute("doctors", doctorsList);
        request.setAttribute("task", "view");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher updateAppointment(HttpServletRequest request) {
        ArrayList<Event> allAppointments;
        RequestDispatcher dispatcher = null;
        try {
            allAppointments = AppointmentDAO.retrieveDoctorsAppointment(" ", false);
            request.setAttribute("appointments", allAppointments);
            request.setAttribute("task", "update");
            dispatcher = request.getRequestDispatcher("AppointmentPages.jsp");

        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dispatcher;
    }

    public RequestDispatcher performGetAction(String action, HttpServletRequest request) {
        RequestDispatcher dispatcher = null;
        if (null != action) {
            switch (action) {
                case "Add":
                    dispatcher = addAppointment(request);
                    break;
                case "Delete":
                    dispatcher = deleteAppointment(request);
                    break;
                case "View":
                    dispatcher = viewAppointments(request);
                    break;
                case "Update":
                    dispatcher = updateAppointment(request);
                    break;
                default:
                    break;
            }
        }

        return dispatcher;
    }

    public RequestDispatcher performPostAction(String action, HttpServletRequest request) {
        RequestDispatcher dispatcher = null;
        if (null != action) {
            String docName = request.getParameter("doctor");
            String date = request.getParameter("date");
            switch (action) {
                case "Confirm":
                    dispatcher = confirmAppointment(request, docName, date);
                    break;
                case "Availability":
                    dispatcher = getAvailabilitySlots(request, docName, date);
                    break;
                case "View Selected":
                    dispatcher = viewSelected(request, docName, date);
                    break;
                case "View All":
                    dispatcher = retrieveAllAppointments(request);
                    break;
                case "Delete Selected":
                    dispatcher = deleteSelected(request);
                    break;
                case "Update Selected":
                    dispatcher = updateSelected(request);
                    break;

            }
        }
        return dispatcher;
    }

    private RequestDispatcher getAvailabilitySlots(HttpServletRequest request, String docName, String date) {
        ArrayList<String> slots;
        int length = Integer.parseInt(request.getParameter("length"));
        slots = getGeneratedSlots(length, docName, date);
        request.setAttribute("doctor", docName);
        request.setAttribute("date", date);
        request.setAttribute("slots", slots);
        request.setAttribute("task", "availableSlots");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher confirmAppointment(HttpServletRequest request, String docName, String date) {
        String[] timeSlots;
        String selectedSlot = request.getParameter("selectedSlot");
        String serviceType = request.getParameter("serviceType");
        String customerName = request.getParameter("customerName");
        String email = request.getParameter("email");
        timeSlots = formatSelectedSlot(selectedSlot);
        Event event = new Event(0, docName, date, timeSlots[0], timeSlots[1], customerName, serviceType, email);
        AppointmentDAO.insertAppointment(event);
        String addMessage = "Successfully added appointment";
        request.setAttribute("successMessage", addMessage);
        request.setAttribute("task", "displayTaskSuccess");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher viewSelected(HttpServletRequest request, String docName, String date) {
        ArrayList<Event> events;
        events = retrieveByDate(docName, date);
        request.setAttribute("appointments", events);
        request.setAttribute("task", "viewSelected");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher deleteSelected(HttpServletRequest request) {
        int count = 0;
        String[] selectedAppointmentsID = request.getParameterValues("selected");
        for (String selectedAppointmentsID1 : selectedAppointmentsID) {
            AppointmentDAO.deleteAppointment(Integer.parseInt(selectedAppointmentsID1));
            count++;
        }

        String deleted = "Successfully deleted " + count + " appointment(s)";
        request.setAttribute("successMessage", deleted);
        request.setAttribute("task", "displayTaskSuccess");
        return request.getRequestDispatcher("AppointmentPages.jsp");
    }

    private RequestDispatcher updateSelected(HttpServletRequest request) {
        int count = 0;
        String[] customerName = request.getParameterValues("customerName");
        String[] appointmentType = request.getParameterValues("type");
        String[] selectedID = request.getParameterValues("selected");
        String[] email = request.getParameterValues("email");
        if (selectedID != null) {
            String temp;
            String[] temp2;
            for (String selectedID1 : selectedID) {
                count++;
                temp = selectedID1;
                temp2 = temp.split("-");
                int rowNumber = Integer.parseInt(temp2[1]);
                int appointmentID = Integer.parseInt(temp2[0]);
                AppointmentDAO.updateAppointment(appointmentID, customerName[rowNumber], appointmentType[rowNumber], email[rowNumber]);
            }
        }
        String updated = "Successfully updated " + count + " appointment(s)";
        request.setAttribute("successMessage", updated);
        request.setAttribute("task", "displayTaskSuccess");
        return request.getRequestDispatcher("AppointmentPages.jsp");

    }

    private ArrayList<Event> retrieveByDate(String doctorName, String date) {
        ArrayList<Event> currEvents = new ArrayList<>();
        try {
            currEvents = AppointmentDAO.retrieveDoctorsAppointment(doctorName, true);
            currEvents = sortAppointmentsByDate(date, currEvents);

        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currEvents;
    }

    private RequestDispatcher retrieveAllAppointments(HttpServletRequest request) {
        ArrayList<Event> allAppointments;
        RequestDispatcher dispatcher = null;
        try {
            allAppointments = AppointmentDAO.retrieveDoctorsAppointment(" ", false);
            request.setAttribute("appointments", allAppointments);
            request.setAttribute("task", "viewAll");
            dispatcher = request.getRequestDispatcher("AppointmentPages.jsp");

        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dispatcher;
    }

}