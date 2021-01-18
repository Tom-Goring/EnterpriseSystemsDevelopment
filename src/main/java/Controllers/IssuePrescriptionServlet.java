/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Event.Log;
import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.Prescription.PrescriptionNotFoundException;
import Models.PrescriptionsApproval.PrescriptionsApproval;
import Models.PrescriptionsApproval.PrescriptionsApprovalDAO;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;

import java.sql.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bredan
 */
public class IssuePrescriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //catch all user input and pass into DAO
        String action = request.getParameter("action");
        if ("repeatPrescriptions".equals(action)) {
            String selectedID = request.getParameter("selectedPrescription");
            if (selectedID != null) {

                int ID = Integer.parseInt(selectedID);

                try {
                    Prescription prescription = PrescriptionDAO.getPrescription(ID);
                    PrescriptionsApproval approval = new PrescriptionsApproval(null, prescription, false);
                    PrescriptionsApprovalDAO.insertApproval(approval);
                } catch (PrescriptionNotFoundException ex) {
                    Logger.getLogger(IssuePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UserNotFoundException ex) {
                    Logger.getLogger(IssuePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                User staffMember = UserDAO.getUser(Integer.parseInt(request.getParameter("submitted-patientid")));
                Prescription prescription = new Prescription(null, staffMember,
                        request.getParameter("submitted-medicine"),
                        Integer.parseInt(request.getParameter("submitted-quantity")),
                        Boolean.parseBoolean(request.getParameter("submitted-repeating")),
                        Date.valueOf(request.getParameter("submitted-issuedate")),
                        Date.valueOf(request.getParameter("submitted-enddate"))
                );
                PrescriptionDAO.insertPrescription(prescription);

                Log.info(String.format("Created new prescription for Patient No. %s ", prescription.getPatient()));
            } catch (UserNotFoundException ex) {
                Logger.getLogger(IssuePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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

}
