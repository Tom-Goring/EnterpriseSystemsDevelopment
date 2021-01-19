package Controllers.Users;

import Models.User.DuplicateEmailPresentException;
import Models.User.UserAccountDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "updateUser", value = "/users/update")
public class Update extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ids = request.getParameterValues("userIDS");
        String[] emails = request.getParameterValues("email");
        String[] firstnames = request.getParameterValues("firstname");
        String[] lastnames = request.getParameterValues("surname");
        String[] roles = request.getParameterValues("role");
        String[] selected = request.getParameterValues("selected");

        if (selected != null) {
            for (int i = 0; i < ids.length; i++) {
                if (Arrays.asList(selected).contains(String.valueOf(i + 1))) {
                    try {
                        UserAccountDAO.updateUserAccountDetails(Integer.parseInt(ids[i]), emails[i], roles[i], firstnames[i], lastnames[i]);
                    } catch (SQLException | DuplicateEmailPresentException ex) {
                        Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
