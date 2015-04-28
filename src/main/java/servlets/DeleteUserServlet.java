package servlets;

import dbconnection.DBConnection;
import exceptions.DBException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        HttpSession session = request.getSession();
        if (session.getAttribute("role").equals("admin")) {
            try {
                DBConnection.deleteUser(login);
                //TODO: IMPORTANT kill session of this user
            } catch (DBException e) {
                response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
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
