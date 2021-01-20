package Controllers.Appointments;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "updateAppointment", value = "/appointments/update")
public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer appointmentID = Integer.parseInt(request.getParameter("appointmentID"));
        Appointment appointment = AppointmentDAO.getAppointmentByID(appointmentID);
        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("/appointments/update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer appointmentID = Integer.parseInt(request.getParameter("appointmentID"));
        Appointment appointment = AppointmentDAO.getAppointmentByID(appointmentID);

        String type = request.getParameter("serviceType");
        assert appointment != null; // god save my soul
        appointment.setType(type);

        AppointmentDAO.updateAppointment(appointment);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
