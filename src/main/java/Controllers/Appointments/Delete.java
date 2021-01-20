package Controllers.Appointments;

import Models.Appointment.AppointmentDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "deleteAppointment", value = "/appointments/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int appointmentToDeleteID = Integer.parseInt(request.getParameter("appointmentID"));
        AppointmentDAO.deleteAppointment(appointmentToDeleteID);
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
