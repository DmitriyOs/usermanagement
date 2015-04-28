package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("role") != null
                && session.getAttribute("role").equals("admin")) {
            String options = "";
            if (!request.getParameter("login").equals("undefined")) {
                String login = request.getParameter("login");
                try {
                    User user = DBConnection.getUser(login);
                    request.setAttribute("oldlogin", user.getLogin());
                    request.setAttribute("newlogin", user.getLogin());
                    request.setAttribute("newpassword", user.getPassword());
                    request.setAttribute("newfullname", user.getFullName());
                    request.setAttribute("newemail", user.getEmail());
                    request.setAttribute("newmobilephone", user.getMobilePhone());

                    options += "<option>" + user.getRole() + "</option>\n";

                    for (String x : DBConnection.getListRoles()) {
                        if (!x.equals(user.getRole())) {
                            options += "<option>" + x + "</option>\n";
                        }
                    }
                    request.setAttribute("options", options);
                } catch (DBException e) {
                    response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
                    return;
                } catch (UserNotFoundException e) {
                    response.sendRedirect(AppParam.getContextPath() + "/start");
                    return;
                }
            } else {
                try {
                    for (String x : DBConnection.getListRoles()) {
                        options += "<option>" + x + "</option>\n";
                    }
                } catch (DBException e) {
                    response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
                    return;
                }
            }
            request.setAttribute("path", AppParam.getContextPath());
            request.setAttribute("options", options);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else {
            response.sendError(response.SC_FORBIDDEN);
            return;
        }

    }
}
