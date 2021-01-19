package Controllers.Users;

import Models.User.*;
import Utils.Tuple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;

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
        Date dob = Date.valueOf(request.getParameter("DOB"));
        Address address = new Address(
                request.getParameter("city"),
                request.getParameter("postcode"),
                request.getParameter("street")
        );
        Gender gender = Gender.fromString(request.getParameter("gender"));
        Type type = Type.fromString(request.getParameter("type"));

        try {
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash(password);
            UserAccount newUserAccount = new UserAccount(
                    new User(
                            null,
                            firstName,
                            surname,
                            email,
                            role,
                            dob,
                            address,
                            gender,
                            type
                    ),
                    saltAndHash.getX(),
                    saltAndHash.getY(),
                    true

            );
            UserAccountDAO.insertUserAccount(newUserAccount);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DuplicateEmailPresentException e) {
            e.printStackTrace();
        }
    }
}
