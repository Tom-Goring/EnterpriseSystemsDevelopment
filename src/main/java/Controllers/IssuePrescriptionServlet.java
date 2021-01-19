/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Event.Log;
import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.User.User;
import Models.User.UserDAO;

import java.sql.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bredan
 */
public class IssuePrescriptionServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //catch all user input and pass into DAO
        String action = request.getParameter("action");
        if ("repeatPrescriptions".equals(action)) {
            System.out.println("i am here");
        } else {
            Prescription prescription = new Prescription(null, Integer.parseInt(request.getParameter("submitted-patientid")),
                    request.getParameter("submitted-medicine"),
                    Integer.parseInt(request.getParameter("submitted-quantity")),
                    Boolean.parseBoolean(request.getParameter("submitted-repeating")),
                    Date.valueOf(request.getParameter("submitted-issuedate")),
                    Date.valueOf(request.getParameter("submitted-enddate"))
            );
            PrescriptionDAO.insertPrescription(prescription);

            Log.info(String.format("Created new prescription for Patient No. %s ", prescription.getPatient()));
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<User> users = UserDAO.getAllUsers();
            request.setAttribute("users", users);
            LocalDate today = java.time.LocalDate.now();
            String minimumDate = today.toString();
            request.setAttribute("minimumDate", minimumDate);
            request.getRequestDispatcher("IssuePrescription.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
