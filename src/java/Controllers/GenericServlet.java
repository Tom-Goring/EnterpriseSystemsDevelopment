
package Controllers;

import Utils.Database;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenericServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = Database.getInstance().getConnection();
        request.setAttribute("HelloWorld", "Hello World!");
        request.setAttribute("DatabaseString", con.toString());
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
    }
}