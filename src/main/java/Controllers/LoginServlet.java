package Controllers;

import Models.Event.Log;
import Models.User.UserAccount;
import Models.User.UserAccountDAO;
import Models.User.UserNotFoundException;
import Utils.Passwords;
import Utils.Tables;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("currentUser") != null) {
            UserAccount user = (UserAccount) session.getAttribute("currentUser");
            session.invalidate();
            request.setAttribute("username", user.getFirstName()+" "+user.getSurname());
            Log.info(String.format("User %s %s logged out", user.getFirstName(), user.getSurname()));
            request.setAttribute("login_failed", false);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            try {
                UserAccount user = UserAccountDAO.getUserAccountByEmail(request.getParameter("submitted-email"));
                boolean passwordsMatch = Passwords.equals(request.getParameter("submitted-password"), user.getSalt(), user.getPassword());

                if (passwordsMatch && user.isActive()) {
                    request.getSession().setAttribute("currentUser", user);
                    Log.info(String.format("User: %s %s successfully logged in", user.getFirstName(), user.getSurname()));
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else if (passwordsMatch && !user.isActive()) {
                    session.setAttribute("approvalNeeded", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else if (!passwordsMatch) {
                    session.setAttribute("loginFailed", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } catch (UserNotFoundException e) {
                session.setAttribute("loginFailed", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("loginFailed", false);
        }
        
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
