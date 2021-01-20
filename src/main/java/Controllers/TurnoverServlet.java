package Controllers;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.AppointmentSlots.SlotPriceDAO;
import Models.AppointmentSlots.SlotPrices;

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

        ArrayList<Long> durations = new ArrayList<>();
        for (Appointment appointment : appointments) {
            durations.add(appointment.getLength().toMinutes());
        }

        SlotPrices slotPrices = SlotPriceDAO.getCurrentSlotPrices();
        assert slotPrices != null;

        Long total = 0L;
        for (Long duration : durations) {
            total += duration;
        }

        int numSlots = (int) Math.round(Math.ceil((float)total / slotPrices.slotSize)); // gross

        BigDecimal totalCost =  slotPrices.getSlotCost().multiply(new BigDecimal(numSlots));

        // couldnt work out how to forward to /dashboard again so we get session variables hurray
        HttpSession session = request.getSession(false);
        session.setAttribute("startPeriod", date);
        session.setAttribute("totalTurnover", totalCost);
        session.setAttribute("numAppointments", appointments.size());

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
