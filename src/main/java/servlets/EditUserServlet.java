package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;
import exceptions.IncorrectUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("role").equals("admin")) {
            String oldlogin = request.getParameter("oldlogin");
            User user = new User(
                    request.getParameter("newlogin"),
                    request.getParameter("newpassword"),
                    request.getParameter("newrole"),
                    request.getParameter("newfullname"),
                    request.getParameter("newemail"),
                    request.getParameter("newmobilephone"));
            try {
                if (oldlogin.length() == 0) {
                    DBConnection.addUser(user);
                } else {
                    DBConnection.updateUser(oldlogin, user);
                    HttpSessionCollector.logout(user.getLogin());
                }
            } catch (DBException e) {
                response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
                return;
            } catch (UserAlreadyExistsException e) {
                request.setAttribute("path", AppParam.getContextPath());
                request.setAttribute("errortext", "User " + user.getLogin() + " already exist");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            } catch (IncorrectUserException e) {
                request.setAttribute("path", AppParam.getContextPath());
                request.setAttribute("errortext", e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            } catch (UserNotFoundException e) {
            }
            response.sendRedirect(AppParam.getContextPath() + "/start");
        } else {
            response.sendError(response.SC_FORBIDDEN);
            return;
        }
    }
}
