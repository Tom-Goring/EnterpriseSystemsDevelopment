package Controllers;

import Models.Approval.Approval;
import Models.Approval.ApprovalDAO;
import Models.Event.Log;
import Models.User.*;
import Utils.Tuple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;

import static Utils.Passwords.createSaltAndHash;
import Utils.Tables;

@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String role = request.getParameter("submitted-role").toLowerCase(Locale.ROOT);
            Tuple<byte[], byte[]> saltAndHash = createSaltAndHash(request.getParameter("submitted-password"));

            User user = new User(
                    null,
                    request.getParameter("submitted-name"),
                    request.getParameter("submitted-surname"),
                    request.getParameter("submitted-email"),
                    request.getParameter("submitted-role"),
                    Date.valueOf(request.getParameter("DOB")),
                    new Address(
                            request.getParameter("city"),
                            request.getParameter("postcode"),
                            request.getParameter("street")
                    ),
                    Gender.fromString(request.getParameter("gender")),
                    Type.fromString(request.getParameter("type"))
            );
            UserAccount userAccount = new UserAccount(
                    user,
                    saltAndHash.x,
                    saltAndHash.y,
                    !UserAccount.isPrivilegedRole(role)
            );
            UserAccountDAO.insertUserAccount(userAccount);
            HttpSession session = request.getSession(false);
            if (UserAccount.isPrivilegedRole(role)) {
                session.setAttribute("approvalNeeded", true);
                UserAccount userWithID = UserAccountDAO.getUserAccountByEmail(user.getEmail());
                Approval approval = new Approval(null, userWithID, false);
                ApprovalDAO.insertApproval(approval);
                Log.info(String.format("User: %s %s requested an account of privilege level %s",
                        userWithID.getFirstName(), userWithID.getSurname(), userWithID.getRole()));
            } else {
                session.setAttribute("createdSuccessfully", true);
            }
            Log.info(String.format("Created new user %s %s with email %s", user.getFirstName(), user.getSurname(), user.getEmail()));
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (DuplicateEmailPresentException | UserNotFoundException e) {
            request.setAttribute("duplicate_email_error", true);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }  catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate today = LocalDate.now();
        String minimumDate = today.toString();
        request.setAttribute("minimumDate", minimumDate);
        request.setAttribute("duplicate_email_error", false);
        request.getRequestDispatcher("register.jsp").forward(request, response);
//        Tables.recreateTables();
    }
 }
