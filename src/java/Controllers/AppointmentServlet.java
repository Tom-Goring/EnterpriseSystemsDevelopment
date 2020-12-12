/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Appointment.Appointment;
import java.io.IOException;
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
        
        
        Appointment app = new Appointment();
        String action;
        action = request.getParameter("action");
        RequestDispatcher dispatcher = app.performGetAction(action, request);
        dispatcher.forward(request, response);        
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Appointment app = new Appointment();
        String action;
        action = request.getParameter("action");
        RequestDispatcher dispatcher = app.performPostAction(action, request);
        dispatcher.forward(request, response); 
        
    }

    
}
