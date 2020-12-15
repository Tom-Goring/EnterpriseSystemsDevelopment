package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    @Override
    public void destroy() {}
    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("currentUser") != null;
        boolean isLoginRequest = request.getServletPath().equals("/login");
        boolean isRegistrationRequest = request.getServletPath().equals("/register");

        // If the user is logged in, they should not be allowed to access the login or register pages, and instead be redirected to their dashboard.
        // If they are not trying to access the login or register pages, we allow them to go where they asked to go.
        // If they are not logged in and trying to access the register or login pages we allow them.
        // If they are not logged in and trying to access any other pages we redirect them to the login page.

        if (loggedIn) {
            if (isLoginRequest && request.getMethod().equals("POST")) {
                chain.doFilter(request, response);
            } else if (isLoginRequest || isRegistrationRequest) {
                String URI = request.getContextPath() + "/dashboard";
                response.sendRedirect(URI);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            String loginURI;
            if (isLoginRequest || isRegistrationRequest) {
                chain.doFilter(request, response);
            } else {
                loginURI = request.getContextPath() + "/login";
                response.sendRedirect(loginURI);
            }

        }
    }
}
