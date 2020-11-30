package Controllers;

import Models.User.DuplicateEmailPresentException;
import Models.User.User;
import Models.User.UserDao;
import Utils.Tuple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static Utils.Passwords.createSaltAndHash;

@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash(request.getParameter("submitted-password"));
            User user = new User(null,
                    request.getParameter("submitted-name"),
                    request.getParameter("submitted-surname"),
                    request.getParameter("submitted-email"),
                    saltAndHash.x,
                    saltAndHash.y
            );

            UserDao.insertUser(user);

        } catch (DuplicateEmailPresentException e) {
            request.setAttribute("duplicate_email_error", true);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }  catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("duplicate_email_error", false);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

 }
