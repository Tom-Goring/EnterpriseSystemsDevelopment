package Controllers;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.AppointmentSlots.SlotPriceDAO;
import Models.AppointmentSlots.SlotPrices;
import Models.User.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "generateTurnover", value = "/turnover")
public class TurnoverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Calculating turnover");
        Date date = Date.valueOf(request.getParameter("date"));
        LocalDate today = LocalDate.now();
        ArrayList<Appointment> appointments = AppointmentDAO.retrieveAllBetweenDates(date, Date.valueOf(today));
        assert appointments != null;

        System.out.println(request.getParameter("nhsOnly"));
        boolean nhsOnly = false;
        if (request.getParameter("nhsOnly") != null) {
            nhsOnly = true;
        }

        ArrayList<Long> nurseDurations = new ArrayList<>();
        ArrayList<Long> doctorDurations = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (nhsOnly) {
                if (appointment.getPatient().getType() == Type.PublicPatient) {
                    if (appointment.getStaffMember().getRole().equals("doctor")) {
                        doctorDurations.add(appointment.getLength().toMinutes());
                    } else {
                        nurseDurations.add(appointment.getLength().toMinutes());
                    }
                }
            } else {
                if (appointment.getStaffMember().getRole().equals("doctor")) {
                    doctorDurations.add(appointment.getLength().toMinutes());
                } else {
                    nurseDurations.add(appointment.getLength().toMinutes());
                }
            }
        }

        SlotPrices slotPrices = SlotPriceDAO.getCurrentSlotPrices();
        assert slotPrices != null;

        Long nurseTotal = 0L;
        Long doctorTotal = 0L;

        for (Long duration : doctorDurations) {
            doctorTotal += duration;
        }

        for (Long duration : nurseDurations) {
            nurseTotal += duration;
        }

        int nurseSlots = (int) Math.round(Math.ceil((float)nurseTotal / slotPrices.slotSize)); // gross

        int doctorSlots = (int) Math.round(Math.ceil((float)doctorTotal / slotPrices.slotSize)); // gross

        BigDecimal nurseCost = slotPrices.getNurseCost().multiply(new BigDecimal(nurseSlots));
        BigDecimal doctorCost = slotPrices.getDoctorCost().multiply(new BigDecimal(doctorSlots));

        BigDecimal totalCost = nurseCost.add(doctorCost);

        // couldnt work out how to forward to /dashboard again so we get session variables hurray
        HttpSession session = request.getSession(false);
        session.setAttribute("startPeriod", date);
        session.setAttribute("totalTurnover", totalCost);
        session.setAttribute("numAppointments", appointments.size());

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
