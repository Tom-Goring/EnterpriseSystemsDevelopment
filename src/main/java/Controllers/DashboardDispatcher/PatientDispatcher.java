package Controllers.DashboardDispatcher;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.Prescription.Prescription;
import Models.Prescription.PrescriptionDAO;
import Models.User.UserAccount;
import Models.User.UserNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDispatcher {
    public static RequestDispatcher dispatch(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserAccount user = (UserAccount) session.getAttribute("currentUser");
        try {
            ArrayList<Prescription> prescriptions = PrescriptionDAO.getAllPrescriptionsForUser(user.getID());
            ArrayList<Appointment> appointments = AppointmentDAO.retrieveAppointments(user);
            
            request.setAttribute("user", user.getFirstName()+user.getSurname());
            request.setAttribute("prescriptions", prescriptions);
            request.setAttribute("appointments", appointments);
            return request.getRequestDispatcher("/dashboards/patient.jsp");
        } catch (SQLException | UserNotFoundException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
