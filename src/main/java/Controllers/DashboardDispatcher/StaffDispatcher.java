package Controllers.DashboardDispatcher;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.User.UserAccount;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class StaffDispatcher {
    public static RequestDispatcher dispatch(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserAccount user = (UserAccount) session.getAttribute("currentUser");

        ArrayList<Appointment> appointments = AppointmentDAO.retrieveAppointments(user);

        request.setAttribute("appointments", appointments);
        return request.getRequestDispatcher("/dashboards/staff.jsp");
    }
}
