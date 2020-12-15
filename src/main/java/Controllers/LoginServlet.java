package Controllers;

import Models.Event.Log;
import Models.User.User;
import Models.User.UserDao;
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
        
        Tables.recreateTables();
        HttpSession session = request.getSession(false);
        if (session.getAttribute("currentUser") != null) {
            User user = (User) session.getAttribute("currentUser");
            session.invalidate();
            Log.info(String.format("User %s %s logged out", user.getFirstName(), user.getSurname()));
            request.setAttribute("login_failed", false);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            try {
                User user = UserDao.getUserByEmail(request.getParameter("submitted-email"));

                if (Passwords.equals(
                        request.getParameter("submitted-password"),
                        user.getSalt(),
                        user.getPassword())
                ) {
                    request.getSession().setAttribute("currentUser", user);
                    Log.info(String.format("User %s %s successfully logged in", user.getFirstName(), user.getSurname()));
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else {
                    request.setAttribute("login_failed", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

            } catch (UserNotFoundException e) {
                request.setAttribute("login_failed", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login_failed", false);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
