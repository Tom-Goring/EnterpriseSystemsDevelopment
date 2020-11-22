package Controllers;

import Models.User.User;
import Models.User.UserDao;
import Models.User.UserNotFoundException;
import Utils.Passwords;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            User user = UserDao.getUserByEmail(request.getParameter("submitted-email"));

            if (Passwords.equals(
                    request.getParameter("submitted-password"),
                    user.getSalt(),
                    user.getPassword())
            ) {
                request.setAttribute("login_failed", false);
                request.getSession().setAttribute("currentUserID", user.getID());

                // placeholder - TODO: replace this with a redirect to the correct dashboard page
                PrintWriter writer = response.getWriter();
                writer.println("Login successful!");
            } else {
                request.setAttribute("login_failed", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch(UserNotFoundException e) {
            request.setAttribute("login_failed", true);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("login_failed", false);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
