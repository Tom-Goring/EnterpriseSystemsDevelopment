package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Models.Event.Event;
import Models.Event.EventDao;
import Models.Event.Log;
import Models.User.*;
import Utils.Tables;

@WebFilter(filterName = "DashboardFilter")
public class DashboardFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("currentUser");

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
        try {
            if (request.getMethod().equals("POST")) {
                Tables.recreateTables();
            }
            List<Event> events = EventDao.getAllEvents();

            request.setAttribute("events", events);
            List<User> users = UserDao.getAllUsers();
            request.setAttribute("users", users);
        } catch (SQLException throwables) {
            Log.info(String.format("Failed to fetch events from database with error message: %s", throwables.toString()));
        }
        return request.getRequestDispatcher("/dashboards/admin.jsp");
    }
}