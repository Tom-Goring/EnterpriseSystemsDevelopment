/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Invoice.Invoice;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;

import java.io.IOException;
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
        try {
            ArrayList<User> users = UserDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("IssueInvoice.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Invoice invoice = new Invoice(
            Integer.parseInt(request.getParameter("submitted-patientid")),
            request.getParameter("submitted-service"),
            Double.parseDouble(request.getParameter("submitted-amount")),
            Date.valueOf(request.getParameter("submitted-issuedate")),
            Date.valueOf(request.getParameter("submitted-duedate"))
        );
        try {
            User user = UserDAO.getUser(Integer.parseInt(request.getParameter("submitted-patientid")));
            request.setAttribute("invoice", invoice);
            request.setAttribute("user", user);
            request.getRequestDispatcher("Invoice.jsp").forward(request, response);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

}
