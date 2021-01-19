package Controllers.Users;

import Models.User.DuplicateEmailPresentException;
import Models.User.UserAccount;
import Models.User.UserAccountDAO;
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

@WebServlet(name = "createUser", value = "/users/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("submitted-name");
        String surname = request.getParameter("submitted-surname");
        String email = request.getParameter("submitted-email");
        String password = request.getParameter("submitted-password");
        String role = request.getParameter("submitted-role");

        try {
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash(password);
            UserAccount newUserAccount = new UserAccount(
                    null,
                    firstName,
                    surname,
                    email,
                    saltAndHash.x,
                    saltAndHash.y,
                    role,
                    true
            );
            UserAccountDAO.insertUserAccount(newUserAccount);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DuplicateEmailPresentException e) {
            e.printStackTrace();
        }
    }
}
