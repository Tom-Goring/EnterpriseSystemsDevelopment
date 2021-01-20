package Controllers.Users;

import Models.User.UserAccountDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteUser", value = "/users/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedUsersID = request.getParameterValues("selected");

        if (selectedUsersID != null) {
            for (String selectedUser : selectedUsersID) {
                UserAccountDAO.deleteUserAccount(Integer.parseInt(selectedUser));
            }
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
