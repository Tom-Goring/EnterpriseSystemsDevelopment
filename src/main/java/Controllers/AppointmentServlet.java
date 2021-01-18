/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.Schedule.Schedule;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;
import Utils.Tuple;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author maxwell
 */
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action;
        User user = (User) request.getSession().getAttribute("currentUser");
        action = request.getParameter("action");
        RequestDispatcher dispatcher = performGetAction(action, request);
        request.setAttribute("username", user.getFirstName() + " " + user.getSurname());
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action;
        action = request.getParameter("action");
        User user = (User) request.getSession().getAttribute("currentUser");
        RequestDispatcher dispatcher = performPostAction(action, request);
        request.setAttribute("username", user.getFirstName() + " " + user.getSurname());
        dispatcher.forward(request, response);
    }

    public RequestDispatcher performGetAction(String action, HttpServletRequest request) {
        RequestDispatcher dispatcher = null;
        if (action != null) {
            switch (action) {
                case "Home":
                    User user = (User) request.getSession().getAttribute("currentUser");
                    ArrayList<Prescription> prescriptions;
                    try {
                        prescriptions = PrescriptionDAO.getAllPrescriptionsForUser(user.getID());
                        request.setAttribute("prescriptions", prescriptions);
                    } catch (SQLException ex) {
                        Logger.getLogger(AppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispatcher = request.getRequestDispatcher("/dashboards/patient.jsp");
                    break;
                default:
                    break;
            }
        } else {
            
            LocalDate today = java.time.LocalDate.now();  
            String minimumDate = today.toString();
            User user = (User) request.getSession().getAttribute("currentUser");
            ArrayList<Appointment> allAppointments;
            ArrayList<User> doctorsList = UserDAO.getAllStaff();
            allAppointments = AppointmentDAO.retrieveAppointments(user);
            
            request.setAttribute("minimumDate", minimumDate);
            request.setAttribute("appointments", allAppointments);
            request.setAttribute("doctors", doctorsList);
            dispatcher = request.getRequestDispatcher("/appointments.jsp");
        }
        return dispatcher;
    }

    public RequestDispatcher performPostAction(String action, HttpServletRequest request) {
        RequestDispatcher dispatcher = null;
        if (action != null) {
            Date date = null;
            try {
                date = Date.valueOf(request.getParameter("date"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (action) {
                case "Confirm":
                    dispatcher = confirmAppointment(request, date);
                    break;
                case "Availability":
                    dispatcher = getAvailabilitySlots(request, date);
                    break;
                case "Delete Selected":
                    dispatcher = deleteSelected(request);
                    break;
                case "Update Selected":
                    dispatcher = updateSelected(request);
                    break;
            }
        } else {
            dispatcher = request.getRequestDispatcher("appointments.jsp");
        }
        return dispatcher;
    }

    private RequestDispatcher getAvailabilitySlots(HttpServletRequest request, Date date) {
        ArrayList<String> slots;
        ArrayList<User> staff;
        ArrayList<ArrayList<String>> allSlots = new ArrayList<>();
        String daySelected = getAvailableDay(date);
        int length = Integer.parseInt(request.getParameter("length"));

        staff = UserDAO.getAvailableStaff(daySelected);
        ArrayList<Tuple<User, ArrayList<String>>> tuples = new ArrayList<>();

        for (User user : staff) {
            slots = AppointmentDAO.getGeneratedSlots(length, user, date);
            allSlots.add(slots);
            tuples.add(
                    new Tuple<>(user, slots)
            );
        }

        request.setAttribute("doctorSlots", tuples);
        request.setAttribute("doctors", staff);
        request.setAttribute("date", date);
        request.setAttribute("slots", allSlots);
        request.setAttribute("task", "availableSlots");

        User user = (User) request.getSession().getAttribute("currentUser");
        ArrayList<Appointment> allAppointments;
        allAppointments = AppointmentDAO.retrieveAppointments(user);
        request.setAttribute("appointments", allAppointments);
        return request.getRequestDispatcher("/appointments.jsp");
    }

    public String getAvailableDay(Date date) {
        Schedule sch = new Schedule();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return sch.getDayOfWeek(day);
    }

    private RequestDispatcher confirmAppointment(HttpServletRequest request, Date date) {
        User patient = (User) request.getSession(false).getAttribute("currentUser");
        Time[] timeSlots = new Time[2];
        String[] selectedSlot = request.getParameterValues("selectedSlot");
        String serviceType = request.getParameter("serviceType");
        try {
            String rawStaffID = request.getParameter("staffID");
            User staffMember = UserDAO.getUser(Integer.parseInt(request.getParameter("staffID")));
            for (String slot : selectedSlot) {
                String[] results = slot.split("/");
                if (results[1].equals(rawStaffID)) {
                    timeSlots = AppointmentDAO.formatSelectedSlot(results[0]);
                }
            }
            Appointment event = new Appointment(null, staffMember, patient, date, timeSlots[0], timeSlots[1], serviceType);
            AppointmentDAO.insertAppointment(event);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        User user = (User) request.getSession().getAttribute("currentUser");
        ArrayList<Appointment> allAppointments;
        allAppointments = AppointmentDAO.retrieveAppointments(user);
        request.setAttribute("appointments", allAppointments);
        return request.getRequestDispatcher("/appointments.jsp");
    }

    private RequestDispatcher deleteSelected(HttpServletRequest request) {

        String[] selectedAppointmentsID = request.getParameterValues("selected");
        if (selectedAppointmentsID != null) {
            for (String selectedAppointmentsID1 : selectedAppointmentsID) {
                AppointmentDAO.deleteAppointment(Integer.parseInt(selectedAppointmentsID1));
            }
        }
        User user = (User) request.getSession().getAttribute("currentUser");
        ArrayList<Appointment> allAppointments;
        allAppointments = AppointmentDAO.retrieveAppointments(user);
        request.setAttribute("appointments", allAppointments);
        return request.getRequestDispatcher("/appointments.jsp");
    }

    private RequestDispatcher updateSelected(HttpServletRequest request) {
        String[] IDs = request.getParameterValues("appointmentIDs");
        String[] staffIDs = request.getParameterValues("staffIDs");
        String[] patientIDs = request.getParameterValues("patientIDs");
        String[] startTimes = request.getParameterValues("startTimes");
        String[] endTimes = request.getParameterValues("endTimes"); // end times indeed...
        String[] dates = request.getParameterValues("dates");
        String[] types = request.getParameterValues("types");
        String[] selected = request.getParameterValues("selected");

        if (selected != null) {
            for (int i = 0; i < IDs.length; i++) {
                if (Arrays.asList(selected).contains(String.valueOf(i + 1))) {
                    try {
                        Appointment appointment = new Appointment(
                                Integer.parseInt(IDs[i]),
                                UserDAO.getUser(Integer.parseInt(staffIDs[i])),
                                UserDAO.getUser(Integer.parseInt(patientIDs[i])),
                                Date.valueOf(dates[i]),
                                Time.valueOf(startTimes[i]),
                                Time.valueOf(endTimes[i]),
                                types[i]
                        );
                        AppointmentDAO.updateAppointment(appointment);
                    } catch (UserNotFoundException | NumberFormatException e) {
                    }
                }
            }
        }
        User user = (User) request.getSession().getAttribute("currentUser");
        ArrayList<Appointment> allAppointments;
        allAppointments = AppointmentDAO.retrieveAppointments(user);
        request.setAttribute("appointments", allAppointments);
        return request.getRequestDispatcher("/appointments.jsp");
    }
}
