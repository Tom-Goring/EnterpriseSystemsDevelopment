package Controllers;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.User.User;
import Models.User.UserDAO;
import Models.User.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = UserDAO.getUser(Integer.parseInt(request.getParameter("userID")));
            ArrayList<Prescription> prescriptions = PrescriptionDAO.getAllPrescriptionsForUser(user.getID());
            ArrayList<Appointment> appointments = AppointmentDAO.retrieveAppointments(user);
            request.setAttribute("username", user.getFirstName()+" "+user.getSurname());
            request.setAttribute("user", user);
            request.setAttribute("prescriptions", prescriptions);
            request.setAttribute("appointments", appointments);
            request.getRequestDispatcher("/patientinformation.jsp").forward(request, response);
        } catch (UserNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
