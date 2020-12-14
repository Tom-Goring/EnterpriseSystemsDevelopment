package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Event.Event;
import Models.Event.EventDao;
import Models.Event.Log;
import Models.Schedule.Schedule;
import Models.Schedule.ScheduleDAO;
import Models.User.*;
import Utils.Tables;

@WebFilter(filterName = "DashboardFilter")
public class DashboardFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        if (!request.getServletPath().equals("/dashboard") && !request.getServletPath().equals("/dashboard/appointments")) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        if (request.getServletPath().equals("/dashboard/appointments")) {
            chain.doFilter(request, response);
            return;
        }

        UserAccount user = (UserAccount) session.getAttribute("currentUser");

        switch (user.getRole()) {
            case "admin":
                adminDispatcher(request).forward(request, response);
                break;
            case "patient":
                request.getRequestDispatcher("/dashboards/patient.jsp").forward(request, response);
                break;
            case "staff":
                request.getRequestDispatcher("/dashboards/staff.jsp").forward(request, response);
                break;
        }
    }

    private RequestDispatcher adminDispatcher(HttpServletRequest request) {
        // We check to see if a POST request to the admin page has been received, and treat this as a a signal to recreate the tables.
        // The button in the admin page sends a POST request to the page, nothing else sends a POST request there.
        // In the future this would be moved to a full Servlet if more functionality was needed.
        try {
            String action = request.getParameter("action");
            if ("Confirm Working Days".equals(action)) {
                String[] rawSchedules = request.getParameterValues("checkrows");
                if (rawSchedules.length > 0) {
                    int index = 0;
                    String[] IDtoDay = rawSchedules[0].split("-");
                    String lastID = IDtoDay[0];
                    String currentID = IDtoDay[0];

                    while(index < rawSchedules.length) {
                        Schedule currentSchedule = new Schedule();
                        currentSchedule.setStaffID(Integer.parseInt(currentID));
                        while (currentID.equals(lastID)) {
                            IDtoDay = rawSchedules[index].split("-");
                            currentSchedule.setDayOfWeek(Integer.parseInt(IDtoDay[1]), true);
                            lastID = currentID;
                            index++;
                            if (index >= rawSchedules.length) {
                                break;
                            } else {
                                currentID = rawSchedules[index].split("-")[0];
                            }
                        }
                        ScheduleDAO.upsertSchedule(currentSchedule);
                        lastID = currentID;
                    }

                }
            }
            if (request.getMethod().equals("POST") && request.getParameter("action").equals("recreate-tables")) {
                Tables.recreateTables();
            }
            List<Event> events = EventDao.getAllEvents();
            request.setAttribute("events", events);
           
            List<User> users = UserDAO.getAllUsers();
            List<User> staff = UserDAO.getAllStaff();

            request.setAttribute("users", users);
            request.setAttribute("staff", staff);
        } catch (SQLException throwables) {
            Log.info(String.format("Failed to fetch events from database with error message: %s", throwables.toString()));
        }
        return request.getRequestDispatcher("/dashboards/admin.jsp");
    }
}
