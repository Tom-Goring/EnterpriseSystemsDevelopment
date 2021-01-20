package Controllers;

import Models.AppointmentSlots.SlotPriceDAO;
import Models.AppointmentSlots.SlotPrices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "slotprice", value = "/slotprice")
public class SlotPriceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int newSlotInterval = Integer.parseInt(request.getParameter("newSlotInterval"));
        BigDecimal newDoctorCost = new BigDecimal(request.getParameter("newDoctorCost"));
        BigDecimal newNurseCost = new BigDecimal(request.getParameter("newNurseCost"));
        int newMaxSlotLength = Integer.parseInt(request.getParameter("newMaxSlotLength"));

        SlotPrices newSlotPrices = new SlotPrices(newSlotInterval, newNurseCost, newDoctorCost, newMaxSlotLength);

        SlotPriceDAO.updateSlotPrices(newSlotPrices);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
