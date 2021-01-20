/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.AppointmentSlots.SlotPriceDAO;
import Models.AppointmentSlots.SlotPrices;
import Models.Invoice.Invoice;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bredan
 */
public class IssueInvoiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Appointment appointment = AppointmentDAO.getAppointmentByID(Integer.parseInt(request.getParameter("appointmentID")));
        Long length = appointment.getLength().toMinutes();
        SlotPrices slotprices = SlotPriceDAO.getCurrentSlotPrices();
        int numSlots = (int) Math.round(Math.ceil((float)length / slotprices.slotSize)); 
        BigDecimal totalCost =  slotprices.getSlotCost().multiply(new BigDecimal(numSlots));
        request.setAttribute("charge", totalCost);
        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("IssueInvoice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ID, Service, Amount
        // the ID of the patient
        Invoice invoice = new Invoice(
            Integer.parseInt(request.getParameter("patientID")),
            request.getParameter("service"),
            Double.parseDouble(request.getParameter("charge")),
            request.getParameter("type"),
            Date.valueOf(request.getParameter("submitted-issuedate")),
            Date.valueOf(request.getParameter("submitted-duedate"))
        );
        try {
            User user = UserDAO.getUser(Integer.parseInt(request.getParameter("patientID")));
            request.setAttribute("invoice", invoice);
            request.setAttribute("user", user);
            request.getRequestDispatcher("Invoice.jsp").forward(request, response);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

}
