package Controllers.Appointments;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.AppointmentSlots.SlotPriceDAO;
import Models.AppointmentSlots.SlotPrices;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;
import Utils.Tuple;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "createAppointment", value = "/appointments/create")
public class Create extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // initial selection of date and length
        // selection of doctor and slot on that date

        if (request.getParameter("stage") != null) {
            Date date = Date.valueOf(request.getParameter("date"));
            int length = Integer.parseInt(request.getParameter("length"));

            ArrayList<String> slots;
            ArrayList<User> staff;
            ArrayList<ArrayList<String>> allSlots = new ArrayList<>();
            String daySelected = Appointment.getDayOfDate(date);

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
            request.setAttribute("stage", "selectingStaffAndSlot");
        } else {
            SlotPrices slotPrices = SlotPriceDAO.getCurrentSlotPrices();
            LocalDate today = LocalDate.now();
            String minimumDate = today.toString();
            request.setAttribute("slotPrices", slotPrices);
            request.setAttribute("minimumDate", minimumDate);
            request.setAttribute("stage", "pickingDateAndLength");
        }
        request.getRequestDispatcher("/appointments/create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User patient = (User) request.getSession(false).getAttribute("currentUser");
        Date date = Date.valueOf(request.getParameter("date"));

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
            String addMessage = "Successfully added appointment";
            request.setAttribute("successMessage", addMessage);
            request.setAttribute("task", "displayTaskSuccess");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
