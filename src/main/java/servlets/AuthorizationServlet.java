package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (DBConnection.databaseIsEmpty()) {
                try {
                    ServletContext context = getServletContext();
                    InputStream file = context.getResourceAsStream("/WEB-INF/my.properties");
                    Properties property = new Properties();
                    property.load(file);
                    if (request.getParameter("login").equals(property.getProperty("login"))
                            && request.getParameter("password").equals(property.getProperty("password"))) {
                        HttpSession session = request.getSession();
                        session.setAttribute("login", property.getProperty("login"));
                        session.setAttribute("role", "admin");
                        response.sendRedirect(AppParam.getContextPath() + "/start");
                    } else {
                        request.setAttribute("path", AppParam.getContextPath());
                        request.setAttribute("haserror", "has-error");
                        request.getRequestDispatcher("/auth.jsp").forward(request, response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    User user = DBConnection.getUser(request.getParameter("login"));
                    if (request.getParameter("login").equals(user.getLogin())
                            && request.getParameter("password").equals(user.getPassword())) {
                        HttpSession session = request.getSession();
                        session.setAttribute("login", user.getLogin());
                        session.setAttribute("role", user.getRole());
                        response.sendRedirect(AppParam.getContextPath() + "/start");
                    } else throw new UserNotFoundException();
                } catch (UserNotFoundException e) {
                    request.setAttribute("path", AppParam.getContextPath());
                    request.setAttribute("haserror", "has-error");
                    request.getRequestDispatcher("/auth.jsp").forward(request, response);
                }
            }
        } catch (DBException e) {
            response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
            return;
        }
    }
}
