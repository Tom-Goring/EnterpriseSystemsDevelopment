package Controllers.DashboardDispatcher;

import Models.Appointment.Appointment;
import Models.Appointment.AppointmentDAO;
import Models.Prescription.PrescriptionDAO;
import Models.PrescriptionsApproval.PrescriptionsApproval;
import Models.PrescriptionsApproval.PrescriptionsApprovalDAO;
import Models.User.UserAccount;
import Models.User.UserAccountDAO;
import Models.User.UserNotFoundException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class StaffDispatcher {

    public static RequestDispatcher dispatch(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserAccount user = (UserAccount) session.getAttribute("currentUser");

        if (request.getMethod().equals("POST")) {
            String action = request.getParameter("action");
            if ("approve".equals(action)) {
                String approved = request.getParameter("approvedPrescription");
                if (approved != null) {
                    PrescriptionsApproval approval = PrescriptionsApprovalDAO.getPrescriptionApproval(Integer.parseInt(approved));
                    approval.setActioned(true);
                    PrescriptionDAO.updatePrescription(approval.getPrescription());
                    PrescriptionsApprovalDAO.updatePrescriptionApproval(approval);
                    
                    
                }
            }
        }
        LocalDate date = LocalDate.now();
        ArrayList<UserAccount> users = UserAccountDAO.getAllUserAccounts();
        ArrayList<Appointment> appointments = AppointmentDAO.retrieveAppointments(user);
        ArrayList<PrescriptionsApproval> approvals = PrescriptionsApprovalDAO.getAllPendingPrescriptionsApprovals();
        request.setAttribute("approvals", approvals);
        request.setAttribute("users", users);
        request.setAttribute("minimumDate", date);
        request.setAttribute("user", user.getFirstName()+user.getSurname());
        System.out.println(approvals);
        request.setAttribute("appointments", appointments);
        return request.getRequestDispatcher("/dashboards/staff.jsp");
    }
}
