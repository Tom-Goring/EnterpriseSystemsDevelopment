package Controllers;

import Utils.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

public class GenericServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = Database.getInstance().getConnection();
        System.out.println("I am hereree");
        System.out.println(con);
        
        request.setAttribute("HelloWorld", "Hello World!");
        request.setAttribute("DatabaseString", con.toString());
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
    }
}
