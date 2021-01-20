package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

import Controllers.DashboardDispatcher.AdminDispatcher;
import Controllers.DashboardDispatcher.PatientDispatcher;
import Controllers.DashboardDispatcher.StaffDispatcher;
import Models.User.*;

@WebFilter(filterName = "DashboardFilter")
public class DashboardFilter implements Filter {

    private final String[] pages = {"appointments", "patientinformation"};

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String final_segment = request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1);

        if (final_segment.equals("turnover")) {
            if (request.getAttribute("totalCost") != null) {
                AdminDispatcher.dispatch(request).forward(request, response);
                return;
            } else {
                chain.doFilter(request, response);
                return;
            }
        }

        if (Arrays.asList(this.pages).contains(final_segment)) {
            chain.doFilter(request, response);
        } else if (final_segment.equals("dashboard")) {
            UserAccount user = (UserAccount) session.getAttribute("currentUser");
            switch (user.getRole()) {
                case "admin":
                    AdminDispatcher.dispatch(request).forward(request, response);
                    break;
                case "patient":
                    PatientDispatcher.dispatch(request).forward(request, response);
                    break;
                case "nurse":
                case "doctor":
                    StaffDispatcher.dispatch(request).forward(request, response);
                    break;
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
